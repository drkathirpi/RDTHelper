package api

import (
	"github.com/TOomaAh/RDTHelper/realdebrid"
	"github.com/gin-gonic/gin"
)

func RegisterTorrent(group *gin.RouterGroup) {

	group.GET("/torrents", func(ctx *gin.Context) {
		torrents, err := realdebrid.GetAll(ctx)
		if err != nil {
			ctx.JSON(500, gin.H{"error": err.Error()})
		} else {
			ctx.JSON(200, torrents)
		}
		torrents = nil
	})
	group.GET("/torrents/:id", getOne)
	group.DELETE("/torrents/:id", realdebrid.DeleteOne)
	group.GET("/torrents/accept/:id", realdebrid.AcceptOne)
	group.POST("/torrent/upload", upload)
	group.POST("/torrents/debrid", Debrid)
}

func getOne(c *gin.Context) {
	torrent := realdebrid.GetOne(c)

	if torrent != nil {
		c.JSON(200, torrent)
	} else {
		c.JSON(404, gin.H{"error": "Torrent not found"})
	}
}

func upload(c *gin.Context) {
	//Upload torrent
	rdtUpload := realdebrid.Upload(c)
	if rdtUpload != nil {
		c.JSON(200, rdtUpload)
	} else {
		c.JSON(500, gin.H{"error": "Error while uploading torrent"})
	}
}

func Debrid(c *gin.Context) {
	//Debrid torrent
	c.JSON(200, realdebrid.Debrid(c))
}
