#Create User:
url: http://localhost:8080/api/users
method: post
{
	"firstName": "Md Taufeeque",
	"lastName": "Alam",
	"username": "taufeeque8",
	"email": "taufeeque8@gmail.com",
	"mobile": "8904360418",
	"accountLocked": false,
	"accountExpired": false,
	"credentialExpired": false,
	"tenant": {
		"id": 1
	},
	"appList": [
		{
			"id": 1
		}
	],
	"roleList": [
		{
			"id": 2
		}
	]
}


##Create App:
url: http://localhost:8080/api/apps
method: post
body:
{
	"appId": "tna",
	"appName": "Time And Action Calender",
	"desc": "Time And Action Calender",
	"appPrefix": "TNA"
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

##Generate License
url: http://localhost:8080/api/licenses
method: post
body:
{
	"tenant": {
		"id":2
	},
	"app": {
		"id": 2
	},
	"validity": 30,
	"entitlementType": "USERS",
	"entitlement": 10,
	"licenseType": "TRIAL",
	"licenseFlavour": "EXPRESS"
}