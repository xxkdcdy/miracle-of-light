<template>
  <div class="container">
    <div class="row">
      <div class="card" style="margin-top: 20px;">
        <div class="card-header">
          <span style="font-size: 130%;">昆虫标注</span>
          <div class="dropdown float-end">
            <button class="btn btn-primary dropdown-toggle" style="margin: 0 10px;" type="button" data-bs-toggle="dropdown" aria-expanded="false">
              操作
            </button>
            <ul class="dropdown-menu">
              <div class="input-group" style="display: none;">
                  <input type="file" class="form-control" id="upLoadPhotoInput" accept="image/*" @change="upload_photo">
              </div>
              <a class="dropdown-item" href="#" @click="upload_input_click">选择图片</a>
              <a class="dropdown-item" href="#" @click="setMode">切换模式</a>
              <li><hr class="dropdown-divider"></li>
              <a class="dropdown-item" href="#" @click="upload_marker">提交数据</a>
            </ul>
          </div>
          <div class="dropdown float-end">
            <button class="btn btn-secondary dropdown-toggle" style="margin: 0 10px;" type="button" data-bs-toggle="dropdown" aria-expanded="false">
              {{ label }}
            </button>
            <ul class="dropdown-menu">
              <li v-for="label in labels" :key="label.id"><a class="dropdown-item" href="#" @click="setLabel(label.name)">{{ label.name }}</a></li>
            </ul>
          </div>
        </div>
        <div class="card-body">
          <div id="map"></div>
        </div>
      </div>
    </div>
  </div>
  <!-- 更新Toast -->
  <div class="toast-container position-fixed bottom-0 end-0 p-3">
      <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
          <div class="toast-header">
              <strong class="me-auto">消息通知</strong>
              <small>几秒前</small>
              <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
          </div>
          <div class="toast-body">
              {{ error_message }}
          </div>
      </div>
  </div>
</template>

<script>
import AILabel from "ailabel"
import $ from 'jquery'
import { ref } from 'vue'
import { Toast } from 'bootstrap/dist/js/bootstrap'

export default {
  data() {
    return {
      imgUrl:
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0186f0570f33d132f875a83991e34b.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1650076295&t=c0b8a135c2f9298d1d714703f5d30423",
      drawingStyle: {},
      mode: "",
      itemName: "",
      editId: "", //待填充图形id
      deleteIconId: "delete001",
      gMap: null, //AILabel实例
      gFirstFeatureLayer: null, //矢量图层实例(矩形，多边形等矢量)
      gFirstTextLayer: null,  //文本图层，显示文本对象
      gFirstImageLayer: null,  //图片图层
      allFeatures: null, //所有features
      allTexts: null, //所有texts
      label: "选择标记昆虫",
      error_message: "",
    };
  },
  watch: {
    mode(mode) {
      this.gMap.setMode(mode);
      this.setDrawingStyle(mode);
    },
  },
  methods: {
    // 切换拖动/标注模式
    setMode() {
      if (this.mode === "RECT") this.mode = "PAN";
      else this.mode = "RECT";
    },
    // 初始样式
    setDrawingStyle(mode) {
      let drawingStyle = {};
      switch (mode) {
        //平移
        case "PAN": {
          break;
        }
        //矩形
        case "RECT": {
          this.drawingStyle = { strokeStyle: "#0f0", lineWidth: 1 };
          this.gMap.setDrawingStyle(drawingStyle);
          break;
        }
        default:
          break;
      }
    },
    // 获取所有features
    getFeatures() {
      this.allFeatures = this.gFirstFeatureLayer.getAllFeatures();
      console.log("--allFeatures--", this.allFeatures);
    },
    // 获取所有Texts
    getTexts() {
      this.allTexts = this.gFirstTextLayer.getAllTexts();
      console.log("--allTexts--", this.allTexts);
    },
    // 添加图形
    addFeature(data, type, id) {
      console.log(type);
      let that = this;
      let drawingStyle = this.drawingStyle;
      const rectFeature = new AILabel.Feature.Rect(
        id, // id
        data, // shape
        { name }, // props
        drawingStyle // style
      );
      that.gFirstFeatureLayer.addFeature(rectFeature);
      this.getFeatures();
      this.getTexts();
    },
    // 添加文本
    addText(x, y, id) {
      let that = this;
      const gFirstText = new AILabel.Text(
        id, // id
        {text: that.label, position: {x: x, y: y}, offset: {x: 0, y: 0}}, // shape
        { name }, // props
        {fillStyle: '#F4A460', strokeStyle: '#D2691E', background: true, globalAlpha: 1, fontColor: '#0f0'} // style
      );
      that.gFirstTextLayer.addText(gFirstText);
    },
    // 增加删除图标
    addDeleteIcon(feature, shape) {
      let gMap = this.gMap;
      let that = this;
      // 添加delete-icon
      // let points = that.getPoints(feature);
      console.log(shape, "shape");
      const gFirstMarker = new AILabel.Marker(
        that.deleteIconId, // id
        {
          src: "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/profile/deleteIcon.png",
          position: { x: shape.x + shape.width, y: shape.y - 15 }, // 矩形右上角 根据图形动态调整
          offset: {
            x: -20,
            y: -25,
          },
        }, // markerInfo
        { name: "delete" } // props
      );
      gFirstMarker.events.on("click", (marker) => {
        // 首先删除当前marker
        gMap.markerLayer.removeMarkerById(marker.id);
        // 删除对应text
        that.gFirstTextLayer.removeTextById(feature.id);
        // 删除对应feature
        that.gFirstFeatureLayer.removeFeatureById(feature.id);
        that.getFeatures();
        that.getTexts();

      });
      gMap.markerLayer.addMarker(gFirstMarker);
 
      // that.gFirstFeatureLayer
    },
    // 删除 删除按钮
    deIcon() {
      this.gMap.markerLayer.removeAllMarkers();
    },
    // 增加事件
    addEvent() {
      let that = this;
      let gMap = this.gMap;
      gMap.events.on("drawDone", (type, data) => {
        console.log("--type, data--", type, data);
        if (that.label === "选择标记昆虫" || (that.allFeatures !== null && that.allFeatures.length >= 1)) return;
        // 这里是画完后可视化结果
        let id = that.getId();
        that.addFeature(data, type, id);
        that.addText(data.x, data.y, id);
      });
      gMap.events.on("boundsChanged", (data) => {
        console.log("--map boundsChanged--", data);
        return "";
      });
      // 双击编辑 在绘制模式下双击feature触发选中
      gMap.events.on("featureSelected", (feature) => {
        this.editId = feature.id;
        console.log("--map featureSelected--", feature, "双击编辑");
        //设置编辑feature
        gMap.setActiveFeature(feature);
        if (feature.type != "POINT") {
          // 增加删除按钮
          that.addDeleteIcon(feature, feature.shape);
        }
      });
      // 单机空白取消编辑
      gMap.events.on("featureUnselected", () => {
        // 取消featureSelected
        that.editId = "";
        that.deIcon();
        gMap.setActiveFeature(null);
      });
      // 更新完
      gMap.events.on("featureUpdated", (feature, shape) => {
        console.log(feature);
        // 更新或者移动需要重新设置删除图标
        that.deIcon();
        feature.updateShape(shape);
        // 找到对应的标注，移动到对应的位置
        let text = null;
        for (let t of that.allTexts) {
          if (t.id === feature.id) {
            text = t;
            break;
          }
        }
        text.updatePosition({x: feature.shape.x, y: feature.shape.y});
        if (feature.type != "POINT") {
          that.addDeleteIcon(feature, shape);
        }
      });
    },
    // 获取坐标 需要自行添加
    getPoints(feature) {
      switch (feature.type) {
        case "RECT":
          return feature.getPoints();
        default:
          return [];
      }
    },
    // 画完生成ID
    getId() {
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
      });
    },
    setLabel(label) {
      this.label = label;
    },
    load_image() {
      let gMap = this.gMap;
      let that = this;
      const gFirstImageLayer = new AILabel.Layer.Image(
        "first-layer-image", // id
        {
          src: that.imgUrl,
          width: 500,
          height: 300,
          crossOrigin: true, // 如果跨域图片，需要设置为true
          position: {
            // 左上角相对中心点偏移量
            x: 0,
            y: 0,
          },
          // 网格
          grid: {
            // 3 * 3
            columns: [{ color: "#9370DB" }, { color: "#FF6347" }],
            rows: [{ color: "#9370DB" }, { color: "#FF6347" }],
          },
        }, // imageInfo
        { name: "图片图层" }, // props
        { zIndex: 5 } // style
      );
      // 添加到gMap对象
      that.gFirstImageLayer = gFirstImageLayer;
      gMap.addLayer(gFirstImageLayer);
    },
    upload_photo() {
      const upLoadPhotoInput = document.getElementById('upLoadPhotoInput');
      const reader = new FileReader();
      reader.readAsDataURL(upLoadPhotoInput.files[0]);
      reader.onload = () => {
        this.imgUrl = reader.result;
        this.gMap.removeLayerById("first-layer-image");
        this.load_image();
      }
    },
    async export_photo(layers) {
      const base64 = await this.gMap.exportLayersToImage(
        {x: 0, y: 0, width: 500, height: 354},
        {layers: layers, type: 'base64', format: 'image/png'}
      );
      return base64;
    },
    async upload_marker() {
      let that = this;
      console.log("---iamge---", that.gFirstImageLayer.imageInfo.width, that.gFirstImageLayer.imageInfo.height);
      if (that.allFeatures === null || that.allFeatures.length === 0 || that.imgUrl.charAt("data:image") !== "d") return;
      else {
        // 标记了数值，进行上传
        // console.log("---export---", that.export_photo([this.gFirstImageLayer, this.gFirstTextLayer, this.gFirstFeatureLayer]));
        // console.log("---export---", that.export_photo([this.gFirstImageLayer]));
        let labels = "&" + that.gFirstImageLayer.imageInfo.height + "&" + that.gFirstImageLayer.imageInfo.width;
        for (let feature of that.allFeatures) {
          let xmin = Math.round(feature.shape.x);
          let ymin = Math.round(feature.shape.y);
          let xmax = Math.round(feature.shape.width) + xmin;
          let ymax = Math.round(feature.shape.height) + ymin;
          let label = "";
          for (let text of that.allTexts) {
            if (text.id === feature.id) {
              label = text.textInfo.text;
              break;
            }
          }
          for (let text of that.labels) {
            if (text.name === label) {
              label = text.pinyin;
              break;
            }
          }
          console.log("---marker---", "xmin", xmin, "ymin", ymin, "xmax", xmax, "ymax", ymax, label);
          //输出格式为 & + height + & + width + # + name1 + @ + x1 + @ + y1 + @ + w1 + @ + h1 + # + name2 + @ + x2 + @ + y2 + @ + w2 + @ + h2
          labels = labels + "#" + label + "@" + xmin + "@" + ymin + "@" + xmax + "@" + ymax;
        }
        console.log("---labels---", labels, that.gFirstImageLayer);
        let photo = await that.export_photo([that.gFirstImageLayer]);
        let mark = await that.export_photo([that.gFirstImageLayer, that.gFirstTextLayer, that.gFirstFeatureLayer])
        $.ajax({
          url: "https://app4460.acapp.acwing.com.cn/api/predict/show/record/mark/add/",
          type: "post",
          data: {
            photo: photo,
            mark: mark,
            label: labels,
          },
          headers: {
            Authorization: "Bearer " + that.$store.state.user.token,
          },
          success(resp) {
            if (resp.error_message === "success") {
              that.error_message = "保存成功！";
              const toastLiveExample = document.getElementById('liveToast');
              const toast = new Toast(toastLiveExample);
              toast.show();
            }
          },
          error(resp) {
            console.log(resp);
          }
        })
      }
    }
  },
  setup() {
    const labels = ref([]);
    $.ajax({
      // 这里移动端记得跟着改
      url: "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/predict/show/labels1.json",
      type: "get",
      success(resp) {
        labels.value = resp;
      },
      error(resp) {
        console.log(resp);
      }
    })

    const upload_input_click = () => {
      const upLoadPhotoInput = document.getElementById('upLoadPhotoInput');
      upLoadPhotoInput.click();
    }

    return {
      labels,
      upload_input_click,
    }
  },
  mounted() {
    let that = this;
    that.setMode();
    const gMap = new AILabel.Map("map", {
      center: { x: 250, y: 150 }, // 为了让图片居中
      zoom: 500,
      mode: "RECT", // 绘制矩形
      refreshDelayWhenZooming: true, // 缩放时是否允许刷新延时，性能更优
      zoomWhenDrawing: true,
      panWhenDrawing: true,
      zoomWheelRatio: 5, // 控制滑轮缩放缩率[0, 10), 值越小，则缩放越快，反之越慢
      withHotKeys: true, // 关闭快捷键
    });
    that.gMap = gMap;
    this.addEvent();
    // 图片层添加
    this.load_image();
    // 添加矢量图层
    const gFirstFeatureLayer = new AILabel.Layer.Feature(
      "first-layer-feature", // id
      { name: "矢量图层" }, // props
      { zIndex: 10 } // style
    );
    this.gFirstFeatureLayer = gFirstFeatureLayer;
    // 添加文本图层
    const gFirstTextLayer = new AILabel.Layer.Text(
      'first-layer-text', // id
      {name: '文本图层'}, // props
      {zIndex: 12, opacity: 1} // style
    );
    this.gFirstTextLayer = gFirstTextLayer;
    gMap.addLayer(gFirstTextLayer);
    gMap.addLayer(gFirstFeatureLayer);

    window.onresize = function () {
      this.gMap && this.gMap.resize();
    };
  },
  beforeUnmount() {
    this.gMap.destroy();
  },
};
</script>

<style scoped>
#map {
  margin: 0 auto;
  overflow: hidden;
  position: relative;
  height: 30rem;
  width: 40rem;
  border: 1px dashed #ccc;
}
.card {
    border: 1px solid rgb(219, 207, 207);
    padding: 12px;
    border-radius: 12px;
    box-shadow: 3px 3px 6px #888;
}
</style>
