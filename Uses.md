#Create User:


##Create App:
url: http://localhost:8080/api/apps
method: post
body:
{
	"appId": "tna",
	"appName": "Time And Action Calender",
	"desc": "Time And Action Calender"
}

##Create Tenant:
url: http://localhost:8080/api/tenants
method: post
body:
{
	"tenantId": "laguna",
	"name": "Laguna Clothing Private Limited",
	"desc": ""
}

##Create Role:
url: http://localhost:8080/api/roles
method: post
body:
{
	"roleId": "merchandiser",
	"name": "Merchandiser",
	"desc": "",
	"isDefault": true,
	"app": {
		"id": 1
	},
	"tenant": {
		"id": 1
	}
}