package web

import (
	"github.com/TOomaAh/RDTHelper/api"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

type LoginError struct {
	Logout bool
	Err    bool
}

func RegisterLogin(group *gin.RouterGroup) {

	var loginError LoginError = LoginError{Logout: false, Err: false}

	group.GET("/logout", func(c *gin.Context) {
		c.SetCookie("token", "", -1, "/", c.Request.Host, false, true)
		loginError.Err = false
		loginError.Logout = true
		c.Redirect(302, "/login")
	})

	group.GET("/login", func(ctx *gin.Context) {
		ctx.HTML(200, "login.html", gin.H{
			"logout": loginError.Logout,
			"err":    loginError.Err,
		})
	})

	group.GET("/signup", func(ctx *gin.Context) {
		ctx.HTML(200, "signup.html", gin.H{})
	})

	group.POST("/perform_signup", func(c *gin.Context) {
		api.PerformSignup(c)
	})
	group.POST("/perform_login", func(c *gin.Context) {
		username := c.PostForm("username")
		password := c.PostForm("password")
		db := c.MustGet("db").(*gorm.DB)
		token, err := api.PerformLogin(username, password, db)

		if err != nil {
			loginError.Logout = false
			loginError.Err = true
			c.Redirect(302, "/login")
		} else {
			c.SetCookie("token", token, 3600, "/", c.Request.Host, false, true)
			c.Redirect(302, "/web/home")
		}

	})

}
