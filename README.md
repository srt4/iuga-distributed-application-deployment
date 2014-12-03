IUGA Distributed Application Deployment
=======================================

Client and server code for deploying applications to servers via LAN Bittorrent.

The use case that this project serves is to deploy very large files (40GB) from one host, to approximately 80 hosts. 

This project leverages bittorrent to maximize throughput and minimize load on the initial seed host.

## Server
The server hosts two endpoints:

##### `GET /`
Displays a list of each registered host, as well as all of the files they are downloading and their progress on the file.

##### `POST /clientStatus`
Endpoint for clients to post their progress to. 

## Client
The client polls, at a regular interval, the following URL: http://www.iuga.info/gamenight/config.php 

This URL was is hardcoded to return JSON structured like so:
```json
{
  "torrentUrl": "http://url/with/list/of/torrents.json",
  "saveDir": "D:\\Example\\Directory\\To\\Save\\To",
  "refreshInterval": 1500,
  "statusUrl": "http://url/to/post/status/to"
}
```
  
`torrentUrl`: The URL for the client to poll to get a list of torrents. <br/>
`saveDir`: The directory where files are saved to.<br/>
`refreshInterval`: How often to poll for new torrents.<br/>
`statusUrl`: The URL for the client to post its progress to.

## Extensibility
This project currently has some hardcoded URLs due to the one-off nature of it. To use this in a different context, 
there are several files that would need updating. 

TODO Document changes needed. 
