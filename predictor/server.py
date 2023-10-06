# -*- coding: UTF-8 -*-
# @File    : server.py


from flask import Flask, jsonify
from flask import request
from flask_cors import CORS

from Main import model
import os
os.environ["KMP_DUPLICATE_LIB_OK"] = "TRUE"

app = Flask(__name__, static_url_path="")
# r'/*' 是通配符，让本服务器所有的 URL 都允许跨域请求
CORS(app, resources=r'/*')


@app.route("/model/healthcheck/", methods=['POST', 'GET'])
def test_hello():
    # logger.info("health")
    res = {"message": "img: BASE64 format image; appKey: Request from administrator;"
                      " use_sr: '1'represents using super-resolution, '0'Represents not using super-resolution."}
    res_json = jsonify(res)
    return res_json


@app.route("/model/predict/", methods=['POST', 'GET'])
def get_data():
    if request.method == 'POST':
        img = request.form.get("img", "")
        appKey = request.form.get("appKey", "")
        use_sr = request.form.get("use_sr", "0")
        try:
            return my_model.predict(img, appKey, use_sr)
        except Exception as e:
            pass
            # logger.error(e)
            # res = function(data)
            # return jsonify(res)


if __name__ == "__main__":
    my_model = model.Model()
    # app.run(host="47.100.35.223", port=3001)
    app.run(host="127.0.0.1", port=3001)
