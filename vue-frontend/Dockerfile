# Use Alpine-based Node image for a smaller footprint
FROM node:alpine

# Set the working directory in the container
WORKDIR /srv/app

# Copy package.json and package-lock.json to the container
COPY package*.json ./

# Install project dependencies quietly
RUN npm install

# Copy the rest of your app's source code from your host to your image filesystem.
COPY . .

# Modify vite.config.js to set server host to 0.0.0.0
RUN sed -i '/export default defineConfig({/a \  server: {\n    host: '\''0.0.0.0'\''\n  },' vite.config.ts

# build app for production with minification
RUN npm run build-only

EXPOSE 5173

ENTRYPOINT ["npm", "run", "start"]