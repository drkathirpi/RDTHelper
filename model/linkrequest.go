package model

import "fmt"

type LinkRequest struct {
	Link string `json:"link"`
}

//toString
func (l *LinkRequest) ToString() string {
	return fmt.Sprintf("%s\n", l.Link)
}
