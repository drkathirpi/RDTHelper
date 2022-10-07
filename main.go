package main

import (
	"fmt"
	"strings"

	"github.com/TOomaAh/RDTHelper/api"
	"github.com/TOomaAh/RDTHelper/database"
	"github.com/TOomaAh/RDTHelper/model"
	"github.com/TOomaAh/RDTHelper/realdebrid"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

type LoginError struct {
	Logout bool
	Err    bool
}

//Cehck if one user exist middleware
func CheckUserExist(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)
	fmt.Printf("%d", model.CountUsers(db))
	if model.CountUsers(db) == 0 {
		c.Redirect(302, "/signup")
		return
	}
	c.Next()
}

//Check if cookie is save
func CheckCookie(c *gin.Context) {
	_, err := c.Cookie("token")
	if err != nil {
		c.Redirect(302, "/login")
		return
	}
	c.Next()
}

func main() {
	r := gin.Default()
	loginError := LoginError{Logout: false, Err: false}

	//register db
	r.Use(func(c *gin.Context) {
		db := database.New()

		c.Set("db", db)
		c.Next()
	})

	r.LoadHTMLGlob("templates/*")
	r.Static("/static", "./static")

	group := r.Group("/api/v1")
	web := r.Group("/web")
	login := r.Group("/")
	web.Use(CheckUserExist)
	web.Use(CheckCookie)
	api.RegisterUser(group)
	api.RegisterTorrent(group)

	web.GET("/", func(c *gin.Context) {
		//redirect to /web
		c.Redirect(302, "/web/home")
	})

	web.GET("/home", func(c *gin.Context) {
		c.HTML(200, "home.html", gin.H{})
	})

	web.POST("/home", func(c *gin.Context) {
		//get all id from query
		var links []string

		ids := c.PostFormArray("id")
		for _, id := range ids {
			torrent := realdebrid.GetOneWithID(c, id)
			links = append(links, torrent.Links[0])
		}

		c.HTML(200, "home.html", gin.H{"links": strings.Join(links, "\n")})
	})

	web.GET("/torrents", func(ctx *gin.Context) {
		ctx.HTML(200, "torrents.html", gin.H{})
	})

	web.GET("/settings", func(ctx *gin.Context) {
		db := ctx.MustGet("db").(*gorm.DB)
		user := model.FindAllUsers(db)[0]

		ctx.HTML(200, "settings.html", gin.H{
			"username": user.Username,
			"rdtToken": user.RdtToken,
			"password": user.Password,
		})
	})

	web.GET("/logout", func(c *gin.Context) {
		c.SetCookie("token", "", -1, "/", "localhost", false, true)
		c.Redirect(302, "/login")
	})

	web.POST("/perform_signup", api.PerformSignup)
	web.POST("/perform_login", api.PerformLogin)

	login.GET("/signup", func(ctx *gin.Context) {
		ctx.HTML(200, "signup.html", gin.H{})
	})

	login.GET("/login", func(ctx *gin.Context) {
		ctx.HTML(200, "login.html", gin.H{
			"logout": loginError.Logout,
			"err":    loginError.Err,
		})
	})

	r.Run()
}
