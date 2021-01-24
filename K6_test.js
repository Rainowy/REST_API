import http from 'k6/http';

export default function () {
  var url = 'http://172.17.0.1:8080/people/';
  var payload = JSON.stringify({
	  name: 'johndoe',
          password: 'PASSWORD',
	  age: '30'
  });

  var params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  http.post(url, payload, params);
}


