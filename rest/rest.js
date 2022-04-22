var express = require("express");
var mysql = require("mysql");
var bodyParser = require("body-parser");
var app = express();
app.use(bodyParser.json());

var connection = mysql.createConnection(
    {
    host     : 'localhost',
    user     : 'root',
    password : '',
    database: 'magasin'
    }
);
connection.connect();

app.get('/getLivreurs',function(req,res)
    {  
    var query = "SELECT * FROM livreur";
    connection.query(query,function(error,results){
    if (error) { throw(error) }
    res.send(JSON.stringify(results));})
    });

app.get('/getCommandes/:idLivreur',function(req,res)
    {  
    var query = "SELECT * FROM Commande where delivred = 0 and idLivreur=?";
    connection.query(query,[req.params.idLivreur],function(error,results){
    if (error) { throw(error) }
    res.send(JSON.stringify(results));})
    }
);
/*
app.get('/getCommandes',function(req,res)
    {  
    var query = "SELECT * FROM Commande where delivred = 0";
    connection.query(query,function(error,results){
    if (error) { throw(error) }
    res.send(JSON.stringify(results));})
    }
);
*/


app.get('/getProduitsCommande/:numCommande',function(req,res)
    {  
    var query = "SELECT p.*, pc.quantite FROM produit p, produitcommande pc where pc.nomProduit = p.nomProduit and pc.numCommande=?";
    connection.query(query,[req.params.numCommande],function(error,results){ 
    if (error) { throw(error) }
    res.send(JSON.stringify(results));})
    }
);


app.get('/getQuantite/:numCommande/:nomProduit',function(req,res)
    {  
    var query = "select * from produitcommande where numCommande=? and nomProduit=?";
    connection.query(query,[req.params.numCommande,req.params.nomProduit],function(error,results){
    if (error) { throw(error) }
    res.send(JSON.stringify(results));})
    }
);



app.delete('/delivrer/:numCommande',function(req,res)
    {  
    var query = "update commande set delivred = 1 WHERE numCommande = ?";
    connection.query(query,[req.params.numCommande],function(error,results){
    if (error) { throw(error) }
    res.send(JSON.stringify(results));})
    }
);

app.delete('/deleteLivraisonByDate/:date',function(req,res)
    {  
    var query = "DELETE FROM livraison where date = ?";
    connection.query(query,[req.params.date],function(error,results){
    if (error) { throw(error) }
    res.send(JSON.stringify(results));})
    }
);



app.post('/postLivraison',function(req,res)
    {  
    var livraison=req.body
    var query =  "INSERT INTO livraison (nbrLivraison,totalEncaissement,date ) VALUES (?,?,?)";
    connection.query(query,[livraison.nbrLivraison, livraison.totalEncaissement, livraison.date],function(error,results){
    if (error) { throw(error) }
    res.send(JSON.stringify("success"));})
    }
);

app.get('/getLivraisonByDate/:date',function(req,res)
    {  
    var query = "select * from livraison where date=? "
    connection.query(query,[req.params.date],function(error,results){
    if (error) { throw(error) }
    res.send(JSON.stringify(results));})
    }
);


var server = app.listen(8091,function()
    {
    var host = server.address().address
    var port = server.address().port
    });