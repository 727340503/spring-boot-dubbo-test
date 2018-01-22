/*!
 * FormatJson v0.1.2
 * Date: 2016-01-28
 * 
 * by zeta
 */
function FormatJson(){
	this.htmlEncode = function(t) {
		return t != null ? t.toString().replace(/&/g, "&amp;").replace(/"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;") : '';
	}	
	this.decorateWithSpan = function(value, className) {
		return '<span class="' + className + '">' + this.htmlEncode(value) + '</span>';
	}
	this.valueToHTML = function(value) {
		var valueType = typeof value, output = "";
		if (value == null)
			output += this.decorateWithSpan("null", "type-null");
		else if (value && value.constructor == Array)
			output += this.arrayToHTML(value);
		else if (valueType == "object")
			output += this.objectToHTML(value);
		else if (valueType == "number")
			output += this.decorateWithSpan(value, "type-number");
		else if (valueType == "string")
			if (/^(http|https):\/\/[^\s]+$/.test(value))
				output += this.decorateWithSpan('"', "type-string") + '<a href="' + value + '" target="_blank">' + this.htmlEncode(value) + '</a>' + this.decorateWithSpan('"', "type-string");
			else
				output += this.decorateWithSpan('"' + value + '"', "type-string");
		else if (valueType == "boolean")
			output += this.decorateWithSpan(value, "type-boolean");
		return output;
	}
	this.arrayToHTML = function(json) {
		var i, length, output = '<div class="collapser"></div>[<span class="ellipsis"></span><ul class="array collapsible">', hasContents = false;
		for (i = 0, length = json.length; i < length; i++) {
			hasContents = true;
			output += '<li><div class="hoverable">';
			output += this.valueToHTML(json[i]);
			if (i < length - 1)
				output += ',';
			output += '</div></li>';
		}
		output += '</ul>]';
		if (!hasContents)
			output = "[ ]";
		return output;
	}
	this.objectToHTML = function(json) {
		var i, key, length, keys = Object.keys(json), output = '<div class="collapser"></div>{<span class="ellipsis"></span><ul class="obj collapsible">', hasContents = false;
		for (i = 0, length = keys.length; i < length; i++) {
			key = keys[i];
			hasContents = true;
			output += '<li><div class="hoverable">';
			output += '<span class="property">' + this.htmlEncode(key) + '</span>: ';
			output += this.valueToHTML(json[key]);
			if (i < length - 1)
				output += ',';
			output += '</div></li>';
		}
		output += '</ul>}';
		if (!hasContents)
			output = "{ }";
		return output;
	}
	this.jsonToHTML = function(json, fnName) {
		var output = '';
		if (fnName)
			output += '<div class="callback-function">' + fnName + '(</div>';
		output += '<div id="json-view">';
		output += this.valueToHTML(json);
		output += '</div>';
		if (fnName)
			output += '<div class="callback-function">)</div>';
		return output;
	}
	this.ontoggle = function(event) {
		var collapsed, target = event.target;
		if (event.target.className == 'collapser') {
			collapsed = target.parentNode.getElementsByClassName('collapsible')[0];
			if (collapsed.parentNode.classList.contains("collapsed"))
				collapsed.parentNode.classList.remove("collapsed");
			else
				collapsed.parentNode.classList.add("collapsed");
		}
	}
	this.getParentLI = function(element) {
		if (element.tagName != "LI")
			while (element && element.tagName != "LI")
				element = element.parentNode;
		if (element && element.tagName == "LI")
			return element;
	}
	this.hoveredLI;
	this.onmouseMove = function(event,self) {
		var str = "", statusElement = document.querySelector(".status");
		element = self.getParentLI(event.target);
		if (element) {
			if (self.hoveredLI)
				self.hoveredLI.firstChild.classList.remove("hovered");
			self.hoveredLI = element;
			element.firstChild.classList.add("hovered");
			do {
				if (element.parentNode.classList.contains("array")) {
					var index = [].indexOf.call(element.parentNode.children, element);
					str = "[" + index + "]" + str;
				}
				if (element.parentNode.classList.contains("obj")) {
					str = "." + element.firstChild.firstChild.innerText + str;
				}
				element = element.parentNode.parentNode.parentNode;
			} while (element.tagName == "LI");
			if (str.charAt(0) == '.')
				str = str.substring(1);
			statusElement.innerText = str;
			return;
		}
		//onmouseOut
		var statusElement = document.querySelector(".status");
		if (this.hoveredLI) {
			this.hoveredLI.firstChild.classList.remove("hovered");
			this.hoveredLI = null;
			statusElement.innerText = "";
		}
	}
	this.selectedLI;
	this.onmouseClick = function(e,self) {
		if (self.selectedLI)
			self.selectedLI.firstChild.classList.remove("selected");
		this.selectedLI = self.getParentLI(event.target);
		if (self.selectedLI) {
			self.selectedLI.firstChild.classList.add("selected");
		}
	}
	this.jsonData;
	this.format = function(viewId,jsonResult){
		var viewParent = document.querySelector(viewId),self = this,child = viewParent.children[0];
		if(child==null||child.id!="json-view")
			self.jsonData = null;
		if(self.jsonData!=(self.jsonData=jsonResult)){
			viewParent.innerHTML = this.jsonToHTML(JSON.parse(jsonResult));
			var view = document.querySelector("#json-view");
			statusElement = document.createElement("div");
			statusElement.className = "status";
			copyPathElement = document.createElement("div");
			copyPathElement.className = "copy-path";
			statusElement.appendChild(copyPathElement);
			view.appendChild(statusElement);
			view.addEventListener('click', this.ontoggle, false);
			view.addEventListener('mouseover', function(){self.onmouseMove(event,self)}, false);
			view.addEventListener('click', function(){self.onmouseClick(event,self)}, false);
		}
	}
}