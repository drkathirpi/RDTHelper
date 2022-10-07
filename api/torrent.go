package api

import (
	"github.com/TOomaAh/RDTHelper/realdebrid"
	"github.com/gin-gonic/gin"
)

func RegisterTorrent(group *gin.RouterGroup) {
	group.GET("/torrents", realdebrid.GetAll)
	group.GET("/torrents/:id", getOne)
	group.DELETE("/torrents/:id", realdebrid.DeleteOne)
	group.GET("/torrents/accept/:id", realdebrid.AcceptOne)
	group.POST("/torrent/upload", realdebrid.Upload)
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

func Debrid(c *gin.Context) {
	//Debrid torrent
	c.JSON(200, realdebrid.Debrid(c))
}
