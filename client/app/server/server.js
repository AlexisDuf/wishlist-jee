/*const express = require('express')
const path = require('path')
const port = process.env.PORT || 8080
const app = express()




// serve static assets normally
app.use(express.static(__dirname))

// handle every other route with index.html, which will contain
// a script tag to your application's JavaScript file(s).
app.get('*', function (request, response){
  response.sendFile(path.resolve(__dirname, './', 'index.html'))
})


/*app.use(function(req, res, next) {
  var router = Router.create({location: req.url, routes: routes})
  router.run(function(Handler, state) {
    var html = React.renderToString(<Handler/>)
    return res.render('react_page', {html: html})
  })
})*/


var express = require('express')
var React = require('react')
var Router = require('react-router')

import {router as routes} from'../app.routes.js';

var app = express()

app.use(function(req, res, next) {
  var router = Router.create({location: req.url, routes: routes})
  router.run(function(Handler, state) {
    var html = React.renderToString(<Handler/>)
    return res.render('react_page', {html: html})
  })
})


app.listen(port)
console.log("server started on port " + port)