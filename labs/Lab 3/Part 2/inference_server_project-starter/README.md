# Overview 

This directory includes a simple inference server that serves a pretrained image classification model.
The top 5 results, including the prediction labels and probabilities, will be returned.

## Setting up the venv:

1) cd into the directory u want the venv to be in

2) create the venv

```bash
python3 -m venv ./.venv        
```

3) once setup, you are going to want to set ur terminal windows source to be the activate script

```bash
source ./.venv/bin/activate
```

4) Once the soruce is set, install all required packages

```bash
# for server
pip install flask torch torchvision
# for client
pip install opencv-python requests
```

## Example Output

If the server setup is successful, you should see the output like this:

```json
{"predictions": [
    {"label":"Doberman","score":0.8287528157234192},
    {"label":"German short-haired pointer","score":0.002210769336670637},
    {"label":"Rhodesian ridgeback","score":0.0014806896215304732},
    {"label":"gas pump","score":0.0010859989561140537},
    {"label":"kelpie","score":0.0009684524266049266}
    ]
}
```

## Testing the Server

To test the functionality of the server, you can use `curl` like this:

```bash
curl -X POST -F "file=@test-images/dog.jpg" http://127.0.0.1:5050/predict
```

## Running the Client

The Python client has been updated to parse and display the top-5 prediction results. Start it in the command line with:

```bash
python client.py
```

