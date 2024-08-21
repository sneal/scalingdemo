# Getting Started

This program is designed solely to demonstrate auto-scaling in TPCF.

## Technologies used:
* Spring Boot 3.3.1
* Java 17
* Gradle
* cf cli

## Running the app locally
```bash
cd scalingdemo
./gradlew bootRun
```


**Note**:

- The application will be pushed using settings in the provided `manifest.yml` file. The output from the command will show the URL that has been assigned to the application.
- Edit the manifest file to change the application name to include a unique ID (May be Org Employee ID) to meet Cloud Foundry's requirement for unique app names

### Push the app in TP CF Platform
```
cd scalingdemo
./gradlew build
cf push
```

## Endpoints
- /cpu **Description**: Performs a CPU-intensive operation by creating multiple threads that perform heavy computations.
- /memory/allocate  **Description**: Starts a memory-intensive operation asynchronously, continuously allocating memory to simulate high memory usage.
- /high_latency **Description**: High latency end point


# Scaling
## Horizontal Scaling
Horizontal scaling involves adding more instances (or replicas) of an application to handle increased load or improve availability.
Cloud Foundry makes it straightforward to scale applications horizontally using the cf scale command.

```
cf scale scalingdemo -i NEW_INSTANCE_COUNT

```

## Viewing Current Instances
To view the current number of instances:

```
cf app scalingdemo
```

## Vertical Scaling

```
cf scale scalingdemo -m NEW_MEMORY

```
**Note**: Vertical scaling causes app to restart.


# Setting Up Autoscaling for TPCF Application

To automate scaling based on application metrics in Tanzu Pivotal Cloud Foundry (TPCF), follow these steps to configure autoscaling using either the Autoscaler service or `manifest.yml` file.

## Pre requisites
Install the App Autoscaler CLI plug-in
Before running App Autoscaler CLI commands, you must install the App Autoscaler CLI plug-in.

## To install the plug-in:

Download the Tanzu App Autoscaler CLI plug-in from VMware Tanzu Network. [Referernce document](https://docs.vmware.com/en/VMware-Tanzu-Application-Service/6.0/tas-for-vms/autoscaler-using-autoscaler-cli.html)

```bash
cf install-plugin ~/Downloads/autoscaler-for-pcf-cliplugin-macosx64-binary-2.0.91
```


## Option 1: Using Autoscaler CLI Plugin

### Step 1: Create Autoscaler Service Instance

```bash
cf create-service autoscaler PLAN_NAME AUTOSCALER_INSTANCE_NAME
```

## Step 2: Bind Autoscaler Service to Application
```bash
cf bind-service APP_NAME AUTOSCALER_INSTANCE_NAME
```

## Step 3: View the apps bound to Auto Scaler
```bash
cf autoscaling-apps
```

## Step 4: To enable autoscale
```
cf enable-autoscaling APP-NAME
```
## Step 5: Configure auto scaling limits
```bash
cf update-autoscaling-limits APP-NAME LOWER-SCALING-LIMIT UPPER-SCALING-LIMIT
```
Where:
APP-NAME is the name of the app for which you want to configure autoscaling limits.
LOWER-SCALING-LIMIT is the minimum number of instances you want Autoscaler to create for the app.
UPPER-SCALING-LIMIT is the maximum number of instances you want Autoscaler to create for the app.

## Step 6: Configure autoscaling factor
```
cf update-autoscaling-factors APP-NAME SCALE-UP-FACTOR SCALE-DOWN-FACTOR
```
Where:

APP-NAME is the name of the app for which you want to configure scaling factors.
SCALE-UP-FACTOR is the number of app instances by which you want Autoscaler to scale up at a time.
SCALE-DOWN-FACTOR is the number of app instances by which you want Autoscaler to scale down at a time.

## Step 7: Scaling can be based on CPU, Memory, HTTP Latency and RabbitMQ Queue Depth. For this exercise we will try HTTP Latency
The following example shows an Autoscaler manifest file with a percentile of 95%, a minimum HTTP Request Latency threshold of 125 milliseconds, and a maximum HTTP Request Latency threshold of 250 milliseconds:

```bash
---
instance_limits:
  min: 1
  max: 4
rules:
- rule_type: http_latency
  rule_sub_type: avg_95th
  threshold:
    min: 125
    max: 250
```
## Step 8: Apply the manifest
```bash
cf configure-autoscaling APP-NAME MANIFEST-FILENAME
cf configure-autoscaling scalingdemo scale.yml
```

## Step 9: Check autoscaling events
```bash
cf autoscaling-events scalingdemo
```
## Option 2: To autoscale an application using Apps Manager in Cloud Foundry, follow these steps:


### Step 1. Log in to Apps Manager
   Open your browser and navigate to the Apps Manager URL provided by your Cloud Foundry environment.
   Log in using your credentials.
### Step 2. Navigate to the Application
   Once logged in, go to the "Applications" tab.
   Locate and click on the application you want to autoscale.
### Step 3. Access the Autoscaling Settings
Click on the name of the app and go to app details page
### Step 4. Enable Autoscaling
   Toggle the switch or checkbox to enable autoscaling for your application.
### Step 5. Configure Autoscaling Rules
   Add a Rule: Click on the option to add a scaling rule.
   Select Metric: Choose a metric to base your scaling on, such as CPU utilization, memory usage, or HTTP throughput.
   Set Thresholds: Define the conditions for scaling up or down. For example:
   Scale up: Increase instances if CPU utilization exceeds 70% for 5 minutes.
   Scale down: Decrease instances if CPU utilization drops below 30% for 5 minutes.
   Set Instance Limits: Define the minimum and maximum number of instances the application can scale to.
   Cool Down Period: Optionally, set a cool-down period to prevent frequent scaling actions.
### Step 6. Review and Save
   Review your scaling rules and instance limits.
   Click "Save" to apply the autoscaling configuration.
### Step 7. Monitor Autoscaling
   After enabling autoscaling, you can monitor how your application scales by returning to the application’s overview page. Here, you can see the current number of instances and scaling events.
### Step 8. Adjust as Needed
   If you notice that the scaling is too aggressive or not responsive enough, return to the autoscaling settings and adjust your rules accordingly.
### Step 9. Disable Autoscaling (if needed)
   If you need to turn off autoscaling, return to the "Autoscaling" section and toggle the switch to disable it.
   By following these steps, you can effectively set up and manage autoscaling for your applications in Cloud Foundry using the Apps Manager.








