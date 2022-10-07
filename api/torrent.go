package api

import "github.com/gin-gonic/gin"

func RegisterTorrent(group *gin.RouterGroup) {
	group.GET("/torrents", getAll)
	group.GET("/torrents/:id", getOne)
	group.DELETE("/torrents/:id", deleteOne)
	group.GET("/torrents/accept/:id", acceptOne)
	group.POST("/torrent/upload", upload)
	group.POST("/torrents/debrid", debrid)
}

func getAll(c *gin.Context) {
}

func getOne(c *gin.Context) {
}

func deleteOne(c *gin.Context) {
}

func acceptOne(c *gin.Context) {
}

func upload(c *gin.Context) {
}

func debrid(c *gin.Context) {
}
