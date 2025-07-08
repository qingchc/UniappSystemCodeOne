"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
if (!Math) {
  (uniEasyinput + uniFormsItem + uniForms)();
}
const uniForms = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-forms/uni-forms.js";
const uniFormsItem = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-forms-item/uni-forms-item.js";
const uniEasyinput = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-easyinput/uni-easyinput.js";
const _sfc_main = {
  __name: "index",
  setup(__props) {
    return (_ctx, _cache) => {
      return {
        a: common_assets._imports_0,
        b: common_vendor.p({
          type: "text",
          prefixIcon: "person",
          placeholder: "请输入邮箱/密码"
        }),
        c: common_vendor.p({
          label: "账号",
          required: true
        }),
        d: common_vendor.p({
          type: "password",
          placeholder: "请输入密码"
        }),
        e: common_vendor.p({
          label: "密码",
          required: true
        }),
        f: common_vendor.p({
          action: "#",
          ["label-align"]: "center"
        })
      };
    };
  }
};
wx.createPage(_sfc_main);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/index/index.js.map
