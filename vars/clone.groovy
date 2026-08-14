def call(String url, String branch){
  echo "Code is cloning from ${url}..."
  git url: url, branch: branch
  echo "Cloning successfully..."
}
