function popImage(imageFile, name, width, height) {
	win = window.open(imageFile, name, 'height=' +
height + ',width=' + width +
',toolbar=no,directories=no,status=no,' +
'menubar=no,scrollbars=no,resizable=no, address=no', false);
win.focus();
}