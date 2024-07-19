# Getting Started

This program is intended only to be used to demonstrate auto scaling in TPCF.


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
Note: Causes the app to restart.


# Setting Up Autoscaling for TPCF Application

To automate scaling based on application metrics in Tanzu Pivotal Cloud Foundry (TPCF), follow these steps to configure autoscaling using either the Autoscaler service or `manifest.yml` file.

## Pre requisites
Install the App Autoscaler CLI plug-in
Before running App Autoscaler CLI commands, you must install the App Autoscaler CLI plug-in.

## To install the plug-in:

Download the Tanzu App Autoscaler CLI plug-in from VMware Tanzu Network. Referernce document [https://docs.vmware.com/en/VMware-Tanzu-Application-Service/6.0/tas-for-vms/autoscaler-using-autoscaler-cli.html] 

```bash
cf install-plugin ~/Downloads/autoscaler-for-pcf-cliplugin-macosx64-binary-2.0.91
```


## Option 1: Using Autoscaler Service

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

