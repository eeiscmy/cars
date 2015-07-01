/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "/CarRestExample/webresources/broadcast3";

if(typeof(EventSource)!=="undefined") {
     
var source = new EventSource(url); 

source.addEventListener("test",function(event) {
    console.log(event.data);
   
}, false);
     
     
 
    
   if (window.XMLHttpRequest) {
    
    // code for IE7+, Firefox, Chrome, Opera, Safari
    xmlhttp=new XMLHttpRequest();
   }
   else {
    // code for IE6, IE5
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
   }  
}
else {
  document.getElementById("test").innerHTML="Sorry, your browser does not support server-sent events...";
}


