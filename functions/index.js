const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.pushNotification = functions.database.ref('notifications/{pushId}').onWrite(event => {
	var valueObject = event.data.val();

	console.log(valueObject.name);
	console.log(valueObject.text);
	console.log(valueObject.hashUserTo);

	const payload = {
		notification:{
			title:valueObject.name,
			body:valueObject.text,
			sound: "default"
		},
	};

	const options = {
		priority: "high",
		timeToLive: 60 * 60 * 24
	};

	


	return admin.messaging().sendToTopic(valueObject.hashUserTo, payload, options);
});