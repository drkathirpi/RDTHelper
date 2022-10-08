package web

import (
	"log"
	"strings"

	"github.com/TOomaAh/RDTHelper/model"
	"github.com/TOomaAh/RDTHelper/realdebrid"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

func RegisterWeb(group *gin.RouterGroup) {
	group.GET("/settings", func(ctx *gin.Context) {
		db := ctx.MustGet("db").(*gorm.DB)
		user := model.FindAllUsers(db)[0]

		ctx.HTML(200, "settings.html", gin.H{
			"username": user.Username,
			"rdtToken": user.RdtToken,
			"password": user.Password,
		})
	})

	group.GET("/torrents", func(ctx *gin.Context) {
		ctx.HTML(200, "torrents.html", gin.H{})
	})

	group.POST("/home", func(c *gin.Context) {
		//get all id from query
		var links []string
		ids := c.PostFormArray("id")
		log.Println(ids)
		for _, id := range ids {
			torrent := realdebrid.GetOneWithID(c, id)
			links = append(links, torrent.Links[0])
		}

		c.HTML(200, "home.html", gin.H{"links": strings.Join(links, "\n")})
	})

	group.GET("/home", func(c *gin.Context) {
		c.HTML(200, "home.html", gin.H{})
	})
}
