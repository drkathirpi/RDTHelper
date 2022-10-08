package main

import (
	"github.com/TOomaAh/RDTHelper/api"
	"github.com/TOomaAh/RDTHelper/database"
	"github.com/TOomaAh/RDTHelper/model"
	"github.com/TOomaAh/RDTHelper/web"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

//Cehck if one user exist middleware
func CheckUserExist(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)
	if model.CountUsers(db) == 0 && c.Request.URL.Path != "/signup" {
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

	//register db
	r.Use(func(c *gin.Context) {
		db := database.New()
		c.Set("db", db)
		c.Next()
	})

	r.LoadHTMLGlob("templates/*")
	r.Static("/static", "./static")

	group := r.Group("/api/v1")
	front := r.Group("/web")
	login := r.Group("/")
	login.Use(CheckUserExist)
	front.Use(CheckUserExist)
	front.Use(CheckCookie)
	api.RegisterUser(group)
	api.RegisterTorrent(group)

	web.RegisterWeb(front)
	web.RegisterLogin(login)

	r.GET("/", func(c *gin.Context) {
		//redirect to /web
		c.Redirect(302, "/web/home")
	})

	r.Run()
}
