hello:
	echo hello

start:
	docker-compose -f docker-compose.yml up

stop:
	docker-compose -f docker-compose.yml down -v
