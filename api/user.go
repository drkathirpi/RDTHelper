package api

import (
	"github.com/TOomaAh/RDTHelper/model"
	"github.com/gin-gonic/gin"
	"golang.org/x/crypto/bcrypt"
	"gorm.io/gorm"
)

func RegisterUser(group *gin.RouterGroup) {
	user := group.Group("/user")
	{
		user.GET("/users", listUsers)
		user.POST("/users", createUser)
		user.POST("/signup", signup)
		user.POST("/login", login)
	}
}

func listUsers(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)
	users := model.FindAllUsers(db)
	c.JSON(200, gin.H{
		"users": users,
	})
}

func PerformSignup(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)
	var user model.User
	user.Username = c.PostForm("username")
	user.Password = c.PostForm("password")
	user.RdtToken = c.PostForm("rdt_token")
	user.Create(db)
	c.JSON(200, gin.H{
		"message": "User created",
	})
}

func createUser(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)
	var user model.User
	if err := c.ShouldBindJSON(&user); err != nil {
		c.JSON(400, gin.H{"error": err.Error()})
		return
	}
	user.Create(db)
	c.JSON(200, gin.H{
		"user": user,
	})
}

func signup(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)
	var user model.User
	if err := c.ShouldBindJSON(&user); err != nil {
		c.JSON(400, gin.H{"error": err.Error()})
		return
	}
	user.Create(db)
	c.JSON(200, gin.H{
		"user": user,
	})
}

func VerifyPassword(password, hashedPassword string) error {
	return bcrypt.CompareHashAndPassword([]byte(hashedPassword), []byte(password))
}

func login(c *gin.Context) {
	var input Authentication
	db := c.MustGet("db").(*gorm.DB)
	if err := c.ShouldBindJSON(&input); err != nil {
		c.JSON(400, gin.H{"error": err.Error()})
		return
	}

	u := model.User{}

	u.Username = input.Username
	u.Password = input.Password

	user := u.FindOneByLogin(db)

	if user.ID == 0 {
		c.JSON(400, gin.H{"error": "Invalid username or password"})
		return
	}

	err := VerifyPassword(user.Password, u.Password)

	if err != nil {
		c.JSON(400, gin.H{"error": "Invalid username or password"})
		return
	}

	token, err := model.GenerateToken(user.ID)

	if err != nil {
		c.JSON(400, gin.H{"error": "Error while generating token"})
		return
	}

	c.JSON(200, gin.H{
		"token": token,
	})

}
