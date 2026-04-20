build:
	docker buildx build \
    	--platform linux/amd64,linux/arm64 \
    	-t kassfequet/flighttracker:latest \
    	--push \
    	.