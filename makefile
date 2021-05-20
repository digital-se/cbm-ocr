deploy_prod:
	docker-compose build pignus_prod
	docker-compose up -d pignus_prod

deploy_homologa:
	docker-compose build pignus_homologa
	docker-compose up -d pignus_homologa

deploy_dev:
	docker-compose build pignus_homologa
	docker-compose up -d pignus_homologa
	docker exec pignus chmod +x /app/install.sh
	docker exec pignus bash -x /app/install.sh
	docker exec pignus chmod 777 /app/.env
	docker exec pignus chmod 777 -R /app/storage
	docker exec pignus chmod 777 -R /app/public
	docker exec pignus chmod 777 -R /app/storage/logs

install_dep:
	docker exec pignus chmod -R 777 /app
	docker exec pignus apt update
	docker exec pignus ls -lah /app
	docker exec pignus chmod +x /app/install.sh
	docker exec pignus bash -x /app/install.sh
	docker exec pignus chmod 777 /app/.env
	docker exec pignus chmod 777 -R /app/storage
	docker exec pignus chmod 777 -R /app/public
	docker exec pignus chmod 777 -R /app/storage/logs
