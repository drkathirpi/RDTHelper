package model

type Link struct {
	ID         string `json:"id"`
	Filename   string `json:"filename"`
	MimeType   string `json:"mimeType"`
	FileSize   int64  `json:"fileSize"`
	Link       string `json:"link"`
	Host       string `json:"host"`
	Chunks     int64  `json:"chunks"`
	Crc        int64  `json:"crc"`
	Download   string `json:"download"`
	Streamable int    `json:"streamable"`
}
