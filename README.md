# Command line instructions


## Git global setup

git config --global user.name "name"

git config --global user.email "name.surname@est.fib.upc.edu"

## Create a new repository

git clone https://gitlab.fib.upc.edu/grau-prop/subgrup-prop7-1.git

cd subgrup-prop7-1

touch README.md

git add README.md

git commit -m "add README"

git push -u origin master

## Existing folder

cd existing_folder

git init

git remote add origin https://gitlab.fib.upc.edu/grau-prop/subgrup-prop7-1.git

git add .

git commit -m "Initial commit"

git push -u origin master

## Existing Git repository

cd existing_repo

git remote add origin https://gitlab.fib.upc.edu/grau-prop/subgrup-prop7-1.git

git push -u origin --all

git push -u origin --tags

