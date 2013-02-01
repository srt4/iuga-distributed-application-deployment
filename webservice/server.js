var config = require('./config.json');
var Mongo = require('mongodb');
var Express = require('express');
var app = Express();

/**
 * begin mongo
 */

var Server = Mongo.Server,
    Db = Mongo.Db;

var server = new Server(config.mongo.host, config.mongo.port, {auto_reconnect: true});
var db = new Db(config.mongo.dbName, server);

db.open(function(err, db){
    if(!err) {
        if(config.debug) {
            console.log("Connected to mongo");
        }
    }
});

/**
 * end mongo
 */


app.configure(function () {
    if(config.debug) {
        app.use(Express.logger('dev'));
    }

    app.use(Express.bodyParser());
    //app.engine('.html', require('jade').__express);
    //app.use(Express.static(path.join(__dirname, 'public')));
});

app.get('/', function(req, res){
    db.collection('clients', function(err, collection) {
        console.log(err);

        collection.find().toArray(function(err, items){
            console.log(err);
            console.log(items);
            res.send(items);
        });
    });
});

app.post('/clientStatus', function(req, res) {
    res.header('Access-Control-Allow-Origin', "*");
    res.header('Access-Control-Allow-Methods', 'POST');
    res.header('Access-Control-Allow-Headers', 'Content-Type');


    var ip = req.param('ip', null);
    var payload = req.param('data', null);

    console.log(payload);

    var torrents = [];

    payload.split("^").forEach(function(torrent){
        if (torrent.split("=").length == 2) {
            var thisTorrent = {
                filename: torrent.split("=")[0],
                percent: parseFloat(torrent.split("=")[1])
            }
            torrents.push(thisTorrent);
        }
    });

    db.collection('clients', function(err, collection) {
        var data = {
            "id": ip,
            "data": torrents,
            "time": new Date()
        };

        collection.update(
            {id: data.id},
            data,
            { safe:true, upsert:true },
            function(err, result){
                console.log("Updated client.");
            }
        );
    });

    res.send("Cool.");
});

app.listen(config.server.port);