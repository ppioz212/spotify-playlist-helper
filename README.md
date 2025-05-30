# 🎵 Spotify Playlist Helper

A full-stack web application that helps you create intelligent Spotify playlists by filtering your existing music library based on various criteria including tempo, albums, playlists, and more.

## ✨ Features

- **🔐 Spotify OAuth Integration** - Secure authentication with your Spotify account
- **📊 Smart Data Collection** - Automatically fetches your albums, playlists, and track audio features
- **🎛️ Advanced Filtering** - Create playlists based on:
  - Specific albums and playlists
  - Tempo range (BPM)
  - Liked songs inclusion/exclusion
- **🚀 Direct Spotify Integration** - Generated playlists are automatically created in your Spotify account
- **💾 Data Persistence** - Your music data is cached for faster subsequent playlist generation

## 🏗️ Architecture

- **Frontend**: Vue.js 3 + TypeScript + PrimeVue
- **Backend**: Java Spring Boot + PostgreSQL
- **Authentication**: Spotify OAuth 2.0
- **Deployment**: Docker containers

## 🚀 Quick Start

### Prerequisites

- Docker and Docker Compose
- Spotify Developer Account
- PostgreSQL database (or use the provided Railway connection)

### 1. Spotify App Setup

1. Go to [Spotify Developer Dashboard](https://developer.spotify.com/dashboard)
2. Create a new app
3. Add redirect URI: `http://localhost:5173` (for local development)
4. Note your Client ID and Client Secret

### 2. Configuration

Update the Spotify credentials in `java-backend/clientconfig.json`:

```json
{
  "clientId": "your_spotify_client_id",
  "secretClientId": "your_spotify_client_secret"
}
```

### 3. Run with Docker

```bash
# Clone the repository
git clone https://github.com/ppioz212/spotify-playlist-helper.git
cd spotify-playlist-helper

# Build and run the backend
cd java-backend
docker build -t spotify-backend .
docker run -p 8080:8080 spotify-backend

# In a new terminal, build and run the frontend
cd vue-frontend
docker build -t spotify-frontend .
docker run -p 5173:5173 spotify-frontend
```

### 4. Access the Application

Open your browser and navigate to `http://localhost:5173`

## 🛠️ Development Setup

### Backend (Java Spring Boot)

```bash
cd java-backend
./gradlew bootRun
```

The backend will start on `http://localhost:8080`

### Frontend (Vue.js)

```bash
cd vue-frontend
npm install
npm run dev
```

The frontend will start on `http://localhost:5173`

## 📁 Project Structure

```
spotify-playlist-helper/
├── java-backend/
│   ├── src/main/java/com/asuresh/spotifyplaylistcompiler/
│   │   ├── controllers/     # REST API endpoints
│   │   ├── model/          # Data models
│   │   ├── services/       # Business logic
│   │   ├── dao/            # Data access objects
│   │   └── utils/          # Utility functions
│   ├── clientconfig.json   # Spotify API credentials
│   └── Dockerfile
└── vue-frontend/
    ├── src/
    │   ├── components/     # Vue components
    │   ├── router/         # Vue Router configuration
    │   └── utils/          # API service functions
    └── Dockerfile
```

## 🔌 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/getAccessToken` | Exchange authorization code for access token |
| GET | `/getUser` | Fetch user profile information |
| GET | `/getPlaylists` | Retrieve user's playlists |
| GET | `/getAlbums` | Retrieve user's saved albums |
| GET | `/compileTracks` | Fetch and analyze all track data |
| GET | `/tracks/min` | Get minimum tempo of user's tracks |
| GET | `/tracks/max` | Get maximum tempo of user's tracks |
| POST | `/generateNewPlaylist` | Create a new filtered playlist |
| GET | `/deleteUser` | Remove user data from database |

## 🎯 How It Works

1. **Authentication**: User logs in with Spotify OAuth
2. **Data Collection**: App fetches user's music library data
3. **Audio Analysis**: Retrieves audio features (tempo, energy, etc.) for all tracks
4. **Playlist Generation**: User selects filtering criteria
5. **Spotify Creation**: New playlist is created directly in user's Spotify account

## 🔧 Technologies Used

### Backend
- Java 17
- Spring Boot 3.1.4
- Spring Data JDBC
- PostgreSQL
- OkHttp3
- Gson
- Gradle

### Frontend
- Vue.js 3
- TypeScript
- Vue Router
- Pinia (State Management)
- PrimeVue (UI Components)
- Axios (HTTP Client)
- Vite (Build Tool)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spotify Web API for providing access to music data
- Spring Boot community for excellent documentation
- Vue.js team for the amazing framework

## 📞 Support

If you encounter any issues or have questions, please open an issue on GitHub.

---

**Note**: This application requires a Spotify Premium account for full functionality, as some Spotify Web API features are limited to Premium users.
