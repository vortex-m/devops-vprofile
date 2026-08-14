def call(){
  echo "Deploying..."
  sh "docker compose up -d"
  echo "Deployment successfully..."
}
