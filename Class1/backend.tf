terraform {
  backend "s3" {
    bucket = "kaizen-aliiar"
    key    = "terraform.tfstate"
    region = "us-east-2"
    use_lockfile = true
  }
}