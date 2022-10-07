package model

import "gorm.io/gorm"

type User struct {
	gorm.Model
	ID       int64
	Username string
	Password string
	ShowAll  bool
	RdtToken string
}

func New(username string, password string, showAll bool, rdtToken string) *User {
	return &User{
		Username: username,
		Password: password,
		ShowAll:  showAll,
		RdtToken: rdtToken,
	}
}

func GetToken(db *gorm.DB) string {
	user := &User{}
	db.First(user)
	if user != nil {
		return user.RdtToken
	}
	return ""
}

func CountUsers(db *gorm.DB) int64 {
	var count int64
	db.Model(&User{}).Count(&count)
	return count
}

func (u *User) Create(db *gorm.DB) *User {
	db.Create(u)
	if db.Error != nil {
		return nil
	}
	u.ID = db.RowsAffected
	return u
}

func (u *User) Update(db *gorm.DB) *User {
	db.Save(u)
	if db.Error != nil {
		return nil
	}
	return u
}

func (u *User) FindOne(db *gorm.DB) *User {
	db.First(u)
	if db.Error != nil {
		return nil
	}
	return u
}

func (u *User) FindOneByLogin(db *gorm.DB) *User {
	db.Where("username = ?", u.Username).First(u)
	if db.Error != nil {
		return nil
	}
	return u
}

func FindAllUsers(db *gorm.DB) []*User {
	var users []*User
	db.Find(&users)
	return users
}
