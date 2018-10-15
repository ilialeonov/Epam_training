
function checkKeyCode() 
{ 
switch (event.keyCode) 
{ 

case 116 : // 'F5' 
event.returnValue = false; 
event.keyCode = 0; 
window.status = "We have disabled F5"; 
break; 
}

} 