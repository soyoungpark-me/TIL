

    server {
    	listen 80;
        listen [::]:80;
    
        server_name dna.soyoungpark.me;
    
        location / {
                proxy_pass_header Server;
                proxy_set_header Host $http_host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Scheme $scheme;
                proxy_pass http://127.0.0.1:9010;
        }
    }
