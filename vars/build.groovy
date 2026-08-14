def call(String name){
  echo "This is building the code..."
  sh "docker build -t ${name} ."
  echo "Build successfully..."
}
