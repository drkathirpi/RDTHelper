package main

import (
	"fmt"

	"github.com/TOomaAh/RDTHelper/api"
	"github.com/TOomaAh/RDTHelper/database"
	"github.com/TOomaAh/RDTHelper/model"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

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

func main() {
	r := gin.Default()

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
	api.RegisterUser(group)
	api.RegisterTorrent(group)

	web.GET("/", func(c *gin.Context) {
		//redirect to /web
		c.Redirect(302, "/web/home")
	})

	web.GET("/home", func(c *gin.Context) {
		c.HTML(200, "home.html", gin.H{})
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

	web.POST("/perform_signup", api.PerformSignup)

	login.GET("/signup", func(ctx *gin.Context) {
		ctx.HTML(200, "signup.html", gin.H{})
	})

	r.Run()
}
