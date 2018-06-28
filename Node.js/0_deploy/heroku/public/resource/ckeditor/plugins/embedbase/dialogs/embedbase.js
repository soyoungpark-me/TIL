/*
 Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.md or http://ckeditor.com/license
*/
CKEDITOR.dialog.add("embedBase",function(c){var b=c.lang.embedbase;return{title:b.title,minWidth:350,minHeight:50,onLoad:function(){var a=this,b=a.getButton("ok");this.on("ok",function(d){d.data.hide=!1;b.disable();d.stop();var e=a.getValueOf("info","url");a.widget.loadContent(e,{noNotifications:!0,callback:function(){a.widget.isReady()||c.widgets.finalizeCreation(a.widget.wrapper.getParent(!0));c.fire("saveSnapshot");b.enable();a.hide()},errorCallback:function(c){a.getContentElement("info","url").select();
b.enable();alert(a.widget.getErrorMessage(c,e,"Given"))}})},null,null,15)},contents:[{id:"info",elements:[{type:"text",id:"url",label:b.url,setup:function(a){this.setValue(a.data.url)},validate:function(){return!this.getDialog().widget.isUrlValid(this.getValue())?b.unsupportedUrlGiven:!0}}]}]}});