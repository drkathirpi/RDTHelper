package model

type Torrent struct {
	ID       string `json:"id"`
	Filename string `json:"filename"`
	//Hash     string `json:"hash"`
	//Bytes    int64  `json:"bytes"`
	//Host     string `json:"host"`
	//Split    int64  `json:"split"`
	Progress int64  `json:"progress"`
	Status   string `json:"status"`
	//Added    string `json:"added"`
	//Files    []*RdtFile `json:"files"`
	Links []string `json:"links"`
	//Ended   int64    `json:"ended"`
	//Speed   int64    `json:"speed"`
	//Seeders int64    `json:"seeders"`
}
