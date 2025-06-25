terraform {
  backend "s3" {
    bucket = "name"
    key    = "terraform.tfstate"
    region = "us-east-2"
    use_lockfile = true
  }
}
