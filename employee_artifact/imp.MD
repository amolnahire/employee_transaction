//
2cfa9324-9080-444d-973a-3a19a1316ff7


Step 1: Log in to Azure
Run these commands on your Ubuntu terminal, where you have Azure CLI installed:
az login


Set your subscription:
az account set --subscription "dd989c95-4624-47b6-9ef5-943f27e6a064"


Get your tenant ID:
az account show --query tenantId -o tsv


Copy the output somewhere safe. This is your Azure tenant ID.
Step 2: Create the service principal
Run this command in your Ubuntu terminal:
az ad sp create-for-rbac \
  --name "employee-jenkins-sp" \
  --skip-assignment \
  --output json


Azure will return JSON similar to this example. These are example values, not your actual credentials.
{
  "appId": "11111111-aaaa-bbbb-cccc-222222222222",
  "displayName": "employee-jenkins-sp",
  "password": "EXAMPLE-CLIENT-SECRET",
  "tenant": "33333333-dddd-eeee-ffff-444444444444"
}


Keep the output safe. The password is the client secret, and Azure does not let you retrieve the same secret value later. If you lose it, create a replacement. 

Microsoft Learn



Step 3: Add the values in Jenkins
Open Jenkins at your Jenkins server.
Go to Manage Jenkins → Credentials → System → Global credentials (unrestricted) → Add Credentials.
Create the first credential:
Credential 1: Azure service principal
Fill these fields using the output from Step 2.
Kind
Username with password

Username
appId — the actual Application (client) ID

Password
password — the actual client secret

ID
azure-service-principal
