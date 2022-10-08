package realdebrid

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"strings"

	"github.com/TOomaAh/RDTHelper/model"
	"github.com/gin-gonic/gin"
	fast "github.com/myussufz/fasthttp-api"

	"gorm.io/gorm"
)

type AuthHeader struct {
	Authorization string `header:"Authorization"`
}

const (
	// APIURL is the base URL for the API
	APIURL = "https://api.real-debrid.com/rest/1.0"
)

func initHeader(c *gin.Context) map[string]string {
	db := c.MustGet("db").(*gorm.DB)

	var user model.User
	id, err := model.ExtractTokenID(c)

	fmt.Println(err)

	if err != nil {
		c.JSON(500, gin.H{"error": err.Error()})
		return nil
	}

	user.ID = id
	user.FindOne(db)

	rdtToken := user.RdtToken

	//create header

	header := map[string]string{
		"Authorization": "Bearer " + rdtToken,
	}
	return header
}

//return JSON response from API
func GetAll(c *gin.Context) {
	//Get All torrent from real debrid
	var torrents []model.Torrent

	header := initHeader(c)

	if header == nil {
		return
	}

	options := fast.Option{
		Method:      http.MethodGet,
		ContentType: fast.ContentTypeJSON,
		Headers:     header,
	}

	if err := fast.Fetch(APIURL+"/torrents", options).ToJSON(&torrents); err != nil {
		log.Println(err)
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}

	c.JSON(200, torrents)
}

func GetOne(c *gin.Context) *model.Torrent {
	return GetOneWithID(c, c.Param("id"))
}

func GetOneWithID(c *gin.Context, id string) *model.Torrent {
	//Get One torrent from real debrid
	var torrent model.Torrent

	header := initHeader(c)

	if header == nil {
		return nil
	}

	options := fast.Option{
		Method:      http.MethodGet,
		ContentType: fast.ContentTypeJSON,
		Headers:     header,
	}

	if err := fast.Fetch(APIURL+"/torrents/info/"+id, options).ToJSON(&torrent); err != nil {
		log.Println(err)
		return nil
	}

	return &torrent
}

func DeleteOne(c *gin.Context) {
	//Delete One torrent from real debrid

	header := initHeader(c)

	if header == nil {
		return
	}

	options := fast.Option{
		Method:      http.MethodDelete,
		ContentType: fast.ContentTypeJSON,
		Headers:     header,
	}

	if _, err := fast.Fetch(APIURL+"/torrents/delete/"+c.Param("id"), options).ToString(); err != nil {
		log.Println(err)
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}

	c.JSON(200, gin.H{"message": "Torrent deleted"})
}

func AcceptOne(c *gin.Context) {
	//Accept One torrent from real debrid

	header := initHeader(c)

	if header == nil {
		return
	}

	body := map[string]string{
		"files": "all",
	}

	options := fast.Option{
		Method:      http.MethodGet,
		ContentType: fast.ContentTypeJSON,
		Headers:     header,
		Body:        body,
	}

	if _, err := fast.Fetch(APIURL+"/torrents/selectFiles/"+c.Param("id"), options).ToString(); err != nil {
		log.Println(err)
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}

	c.JSON(200, gin.H{"message": "Torrent accepted"})
}

func Upload(c *gin.Context) {
	//Upload torrent to real debrid
	header := initHeader(c)

	if header == nil {
		return
	}

	from, err := c.MultipartForm()

	if err != nil {
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}

	files := from.File["file"]

	for _, file := range files {

		url := "https://api.real-debrid.com/rest/1.0/torrents/addTorrent"
		method := "PUT"
		//get content file
		content, err := file.Open()
		if err != nil {
			c.JSON(500, gin.H{"error": err.Error()})
			return
		}
		defer content.Close()
		bytes, _ := ioutil.ReadAll(content)
		payload := strings.NewReader(string(bytes))

		client := &http.Client{}
		req, err := http.NewRequest(method, url, payload)

		if err != nil {
			fmt.Println(err)
			return
		}
		req.Header.Add("Authorization", "Bearer 7LQ4JRO6URYYWDYTBNYN2YHSOWHAOEJHK7PMCE4BV252H5NZ5XFQ")
		req.Header.Add("Content-Type", "application/x-bittorrent")

		res, err := client.Do(req)
		if err != nil {
			fmt.Println(err)
			return
		}
		defer res.Body.Close()

		_, err = ioutil.ReadAll(res.Body)
		if err != nil {
			fmt.Println(err)
			return
		}

	}

	c.JSON(200, gin.H{"message": "Torrent uploaded"})
}

func Debrid(c *gin.Context) *model.Link {
	//Debrid torrent from real debrid

	header := initHeader(c)

	if header == nil {
		return nil
	}

	//get link from body
	var linkRequest model.LinkRequest
	var link model.Link
	if err := c.ShouldBindJSON(&linkRequest); err != nil {
		log.Println(err)
		return nil
	}

	body := map[string]string{
		"link": linkRequest.Link,
	}
	options := fast.Option{
		Method:      http.MethodPost,
		ContentType: "application/x-www-form-urlencoded",
		Headers:     header,
		Body:        body,
	}

	if err := fast.Fetch(APIURL+"/unrestrict/link", options).ToJSON(&link); err != nil {
		log.Println(err)
		return nil
	}
	return &link
}
