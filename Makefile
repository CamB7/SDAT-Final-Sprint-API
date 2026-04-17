build:
	docker buildx build \
    	--platform linux/amd64,linux/arm64 \
    	-t CamB7/FlightTracker:latest \
    	--push \
    	.