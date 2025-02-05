# Stage 1: Build the Angular application
FROM node:18 AS build
WORKDIR /app

# Add npm configuration for better network resilience
RUN npm config set fetch-retry-maxtimeout 600000 \
    && npm config set fetch-retry-mintimeout 10000 \
    && npm config set fetch-retries 5

# Install dependencies first (better layer caching)
COPY package*.json ./
RUN npm install --legacy-peer-deps --fetch-retries=5 --fetch-retry-factor=5 --fetch-retry-mintimeout=20000 --fetch-retry-maxtimeout=120000

# Copy the rest of the application
COPY . .

# Build the application
RUN npm run build

# Stage 2: Serve the application with Nginx
FROM nginx:alpine
COPY --from=build /app/dist/music-stream/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Add healthcheck
HEALTHCHECK --interval=30s --timeout=3s \
    CMD wget -q --spider http://localhost:80/ || exit 1

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"] 