<template>
  <div class="container" id="song-back-addb">
    <div class="assessment">
      <!-- <el-row>
  <el-col :span="12" ><div><el-button class="ass-top">附件</el-button></div></el-col>
      </el-row>-->
    </div>
    <div class="ass-input">
      <el-row :gutter="60">
        <el-form
          :model="model"
          :data="detailDodel"
          label-width="100px"
          @submit.native.prevent
        >
          <div>
            <el-col :span="24" class="bri-pictures">
              <div class="tiShCl">
                <div>
                  <el-button
                    type="primary"
                    size="small"
                    @click="showRowPhoto()"
                    class="addPictures"
                    >添加图片</el-button
                  >
                </div>

                <div>
                  <el-button
                    type="primary"
                    size="small"
                    class="attachment"
                    @click="showAddAnnexModel()"
                    >上传附件</el-button
                  >
                </div>
              </div>
            </el-col>

            <!-- 添加照片弹框 -->

            <el-col :span="24">
              <el-dialog
                class="photo"
                title="添加照片"
                :visible.sync="starPhotoModal"
                width="30%"
                :close-on-click-modal="false"
              >
                <div style="height=300px; width=400px" class="fileHeader">
                  <!-- <el-upload
                    class="avatar-uploader"
                    ref="upload"
                    action="http://localhost:8080/bridge/Upload/fileUpload"
                    :show-file-list="false"
                    :on-success="  handleAvatarSuccess"
                    :data="uploadData"
                    :before-upload="beforeAvatarUpload"
                    :auto-upload="false"
                  > -->

                  <!-- <el-upload
                    class="avatar-uploader"
                    ref="upload"
                    action="http://172.16.6.222:8080/bridge/Upload/fileUpload"
                    :show-file-list="false"
                    :on-success="  handleAvatarSuccess"
                    :data="uploadData"
                    :before-upload="beforeAvatarUpload"
                    :auto-upload="false"
                  > -->

                  <!-- 
                    <el-upload
                    class="avatar-uploader"
                    ref="upload"
                    action="/bridge/Upload/fileUpload"
                 
                    :show-file-list="false"
                    :on-success="  handleAvatarSuccess"
                    :data="uploadData"
                    :before-upload="beforeAvatarUpload"
                    :auto-upload="false"
                  >


                    <img
                      v-if="imageUrl"
                      :src="imageUrl"
                      class="avatar"
                      style="height: 100%;width: 100%;"
                    />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload> -->

                  <el-upload
                    ref="upload"
                    action="/bridge/Upload/modelUpload"
                    list-type="picture-card"
                    :data="uploadData"
                    :before-upload="beforeAvatarUpload"
                    :on-success="handleAvatarSuccess"
                    :auto-upload="false"
                  >
                    <img
                      v-if="imageUrl"
                      :src="imageUrl"
                      class="avatar"
                      style="height: 100%; width: 100%"
                    />
                    <i class="el-icon-plus"></i>
                    <div slot="tip" class="el-upload__tip">
                      上传zip压缩包，不超过30mb，obj和mtl的名称需和压缩包名称相同
                    </div>
                  </el-upload>

                  <span
                    class="fileDown"
                    style="
                      margin-top: 20px;
                      display: block;
                      font-size: 16px;
                      color: #e3e8f7;
                    "
                    >备注：</span
                  >
                  <el-input
                    class="el-textarea__inner"
                    type="textarea"
                    v-model="uploadData.remarks"
                    :maxlength="200"
                    placeholder="200字以内"
                  ></el-input>
                </div>
                <div class="fileDown" style="padding-top: 0px; float: right">
                  <el-button
                    size="small"
                    type="primary"
                    @click="submitUpload"
                    class="submitUploadCl"
                    >确定</el-button
                  >
                  <el-button
                    size="small"
                    @click="starPhotoModal = false"
                    class="cannelUploadCl"
                    >取消</el-button
                  >
                </div>
              </el-dialog>

              <!-- 添加附件弹框 -->
              <el-dialog
                class="photo"
                title="上传附件"
                :visible.sync="starAnnexModal"
                width="30%"
                :close-on-click-modal="false"
              >
                <div style="height=300px; width=400px" class="fileHeader">
                  <!-- <el-upload
                    class="upload-demo"
                    ref="upload"
                    action="http://localhost:8080/bridge/Upload/fileUpload"
                    :on-success="handleAnnexSuccess"
                    :data="uploadAnnexData"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :file-list="fileList"
                    :auto-upload="false"
                  > -->

                  <el-upload
                    class="upload-demo"
                    ref="upload"
                    action="/bridge/Upload/fileUpload"
                    :on-success="handleAnnexSuccess"
                    :data="uploadAnnexData"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :file-list="fileList"
                    :auto-upload="false"
                  >
                    <el-button
                      slot="trigger"
                      size="small"
                      type="primary"
                      class="submitUploadCl"
                      >选取文件</el-button
                    >
                    <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
                  </el-upload>
                  <span
                    class="fileDown"
                    style="
                      margin-top: 20px;
                      display: block;
                      font-size: 16px;
                      color: #e3e8f7;
                    "
                    >备注：</span
                  >
                  <el-input
                    class="el-textarea__inner"
                    type="textarea"
                    v-model="uploadAnnexData.remarks"
                    :maxlength="200"
                    placeholder="200字以内"
                  ></el-input>
                </div>
                <div class="fileDown" style="padding-top: 0px; float: right">
                  <el-button
                    size="small"
                    type="primary"
                    @click="submitAnnexUpload"
                    class="submitUploadCl"
                    >确定</el-button
                  >
                  <el-button
                    size="small"
                    @click="starAnnexModal = false"
                    class="cannelUploadCl"
                    >取消</el-button
                  >
                </div>
              </el-dialog>

              <!-- <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button> -->

              <!-- 照片走马灯弹框 -->
              <el-dialog
                id="showPhotoModalId"
                class="photo"
                title="添加图片"
                :visible.sync="showPhotoModal"
                width="30%"
                :close-on-click-modal="false"
              >
                <div style="height=300px; width=400px" class="photoLb">
                  <el-carousel
                    indicator-position="outside"
                    v-if="
                      selectPhotoList !== undefined &&
                      selectPhotoList.length > 0
                    "
                    class="photoLb"
                    height="150px"
                    :autoplay="false"
                    ref="carouselPhoto"
                    @change="carouselChange"
                  >
                    <el-carousel-item
                      v-for="item in selectPhotoList"
                      :key="item.index"
                      class="img-item"
                    >
                      <img :src="item.photoUrl" class="imgItems" />
                    </el-carousel-item>
                  </el-carousel>
                  <span
                    style="
                      margin-top: 20px;
                      display: block;
                      font-size: 16px;
                      color: #e3e8f7;
                    "
                    >备注：{{ nowPhoto.remarks }}</span
                  >
                </div>
                <div style="padding-bottom: 50px; float: right">
                  <el-button
                    type="primary"
                    class="showAddModelCl"
                    size="small"
                    @click="showAddModel"
                    >添加</el-button
                  >
                  <el-button
                    type="primary"
                    class="delPhotoCl"
                    size="small"
                    @click="delPhoto"
                    v-bind:disabled="disabled"
                    >删除</el-button
                  >
                </div>
              </el-dialog>
            </el-col>

            <el-col :span="24" class="bri-size">一般资料</el-col>
            <!-- <el-col :span="6">
            <el-form-item label="桥梁名称：" >
              <el-input
                v-model="model.structureName"
                clearable
                
                />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="施工单位：" >
              <el-input
                v-model="model.constructionDepartment"
                clearable
                
                />
            </el-form-item>
            </el-col>-->
            <el-col :span="6">
              <el-form-item label="设计荷载：">
                <el-input
                  v-model="model.designLoad"
                  clearable
                  :maxlength="30"
                  placeholder="30字以内"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="桥梁总长：" required>
                <div class="unit">
                  <div class="unit-st">m</div>
                </div>
                <el-input
                  v-model="model.bridgeTotalLength"
                  :maxlength="30"
                  placeholder="30字以内"
                  id="bridgeTotalLeng"
                />
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="限载标准：">
                <el-input
                  v-model="model.postingStandard"
                  clearable
                  :maxlength="30"
                  placeholder="30字以内"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="桥梁总宽：" required>
                <div class="unit">
                  <div class="unit-st">m</div>
                </div>
                <el-input
                  v-model="model.bridgeTotalWidth"
                  :maxlength="30"
                  placeholder="30字以内"
                  id="bridgeTotalWid"
                />
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="总造价：" required>
                <div class="unit">
                  <div class="unit-st">万元</div>
                </div>
                <el-input
                  v-model="model.cost"
                  :maxlength="30"
                  placeholder="30字以内"
                  id="costt"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="抗震烈度：">
                <el-input
                  v-model="model.quakeIntensity"
                  clearable
                  :maxlength="30"
                  placeholder="30字以内"
                />
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="车行道净宽：" required>
                <div class="unit">
                  <div class="unit-st">m</div>
                </div>
                <el-input
                  v-model="model.roadwayWidth"
                  :maxlength="30"
                  placeholder="30字以内"
                  id="roadwayWid"
                />
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="养护类别：">
                <el-input
                  v-model="model.maintainCategory"
                  clearable
                  :maxlength="30"
                  placeholder="30字以内"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="正斜交角：">
                <el-input
                  v-model="model.skewAngle"
                  clearable
                  :maxlength="30"
                  placeholder="30字以内"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="人行道净宽：" required>
                <div class="unit">
                  <div class="unit-st">m</div>
                </div>
                <el-input
                  v-model="model.sidewalkWidth"
                  :maxlength="30"
                  placeholder="30字以内"
                  id="sidewalkWid"
                />
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="养护等级：">
                <el-input
                  v-model="model.maintainGrade"
                  clearable
                  :maxlength="30"
                  placeholder="30字以内"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="桥梁跨数：" required>
                <div class="unit">
                  <div class="unit-st">跨</div>
                </div>
                <el-input
                  v-model="model.bridgeSpan"
                  :maxlength="30"
                  placeholder="30字以内"
                  id="bridgeSp"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="河道等级：">
                <el-input
                  v-model="model.streamwayGrade"
                  clearable
                  :maxlength="30"
                  placeholder="30字以内"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="设计单位：">
                <el-input
                  v-model="model.designUnits"
                  clearable
                  :maxlength="30"
                  placeholder="30字以内"
                />
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="跨径组合：">
                <el-input
                  v-model="model.spanCombination"
                  clearable
                  :maxlength="30"
                  placeholder="30字以内"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="最高水位：" required>
                <div class="unit">
                  <div class="unit-st">m</div>
                </div>
                <el-input
                  v-model="model.highestStage"
                  :maxlength="30"
                  placeholder="30字以内"
                  id="highestSta"
                />
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="结构类型：">
                <el-input
                  v-model="model.structureType"
                  clearable
                  :maxlength="30"
                  placeholder="30字以内"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="桥面面积：" required>
                <div class="unit">
                  <div class="unit-st">㎡</div>
                </div>
                <el-input
                  v-model="model.bridgeArea"
                  :maxlength="30"
                  placeholder="30字以内"
                  id="bridgeAr"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="常水位：" required>
                <div class="unit">
                  <div class="unit-st">m</div>
                </div>
                <el-input
                  v-model="model.usualStage"
                  :maxlength="30"
                  placeholder="30字以内"
                  id="usualSta"
                />
              </el-form-item>
            </el-col>
          </div>

          <el-col :span="24" class="bri-size">上部结构</el-col>

          <!-- <hr>上部结构</hr> -->

          <!-- <div class="ass-input">
    <el-row :gutter="60">
          <el-form :model="model"  label-width="85px" @submit.native.prevent @keyup.enter.native="getList" >-->

          <el-col :span="6">
            <el-form-item label="主梁形式：">
              <el-input
                v-model="model.upMainBeamForm"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="桥下限高：" required>
              <el-input
                v-model="model.lowerLimit"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="lowerLim"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="桥面结构：">
              <el-input
                v-model="model.upDeckComposition"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="主桥纵坡：">
              <el-input
                v-model="model.upMainLongitudinalSlope"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="主梁尺寸：" required>
              <el-input
                v-model="model.upMainBeamSize"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="upMainBeamSi"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="拱桥矢跨比：">
              <el-input
                v-model="model.upRiseSpan"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="桥面铺装厚度：" required>
              <el-input
                v-model="model.pavementLand"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="pavementLa"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="主桥横坡：">
              <el-input
                v-model="model.upMainCrossSlope"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="主梁数量：" required>
              <el-input
                v-model="model.upMainBeamQuantity"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="upMainBeamQuanti"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="支座型式：">
              <el-input
                v-model="model.upBearingForm"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="伸缩缝型式：">
              <el-input
                v-model="model.upExpansionForm"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="引桥纵坡：">
              <el-input
                v-model="model.upApproachLongitudinalSlope"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="横梁形式：">
              <el-input
                v-model="model.upCrossBeamForm"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="支座数量：" required>
              <el-input
                v-model="model.upBearingNum"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="upBearingNu"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="伸缩缝数量：" required>
              <el-input
                v-model="model.upExpansionQuantity"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="upExpansionQuanti"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="引桥横坡：">
              <el-input
                v-model="model.upApproachSlope"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="主跨桥下净空：">
              <el-input
                v-model="model.clearanceSpan"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>

          <el-col :span="24" class="bri-size">下部结构-桥墩</el-col>

          <!-- <hr>下部结构-桥墩</hr>

  <div class="ass-input">
    <el-row :gutter="60">
          <el-form :model="model"  label-width="85px" @submit.native.prevent @keyup.enter.native="getList" >-->
          <el-col :span="6">
            <el-form-item label="形式：">
              <el-input
                v-model="model.pierForm"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="桥墩标高：" required>
              <el-input
                v-model="model.pierElevation"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="pierElevati"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="基地标高：" required>
              <el-input
                v-model="model.pierBaseElevation"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="pierBaseElevati"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="桥墩数量：" required>
              <el-input
                v-model="model.pierNum"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="pierNumm"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="盖梁尺寸：" required>
              <el-input
                v-model="model.pierCapSize"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="pierCapSi"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="底板尺寸：" required>
              <el-input
                v-model="model.pierFloorSize"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="pierFloSize"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="基桩根数：" required>
              <el-input
                v-model="model.pierPileNum"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="pierPileNumm"
              />
            </el-form-item>
          </el-col>
          <!-- </el-form>
    </el-row>
          </div>-->
          <el-col :span="24" class="bri-size">下部结构-桥台</el-col>

          <!-- <hr>下部结构-桥台</hr>

  <div class="ass-input">
    <el-row :gutter="60">
          <el-form :model="model"  label-width="85px" @submit.native.prevent @keyup.enter.native="getList" >-->
          <el-col :span="6">
            <el-form-item label="形式：">
              <el-input
                v-model="model.abutmentForm"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="基底标高：" required>
              <el-input
                v-model="model.abutmentBaseElevation"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="abutmBaseElevation"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="基桩尺寸：" required>
              <el-input
                v-model="model.abutmentPileSize"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="abutmentPileSizee"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="翼墙形式：">
              <el-input
                v-model="model.abutmentWingWallForm"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="桥台数量：" required>
              <el-input
                v-model="model.abutmentNum"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="abutmentNumm"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="台帽尺寸：" required>
              <el-input
                v-model="model.abutmentCapSize"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="abutmentCapSizee"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="基桩根数：" required>
              <el-input
                v-model="model.abutmentPileNum"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="翼墙长度：" required>
              <el-input
                v-model="model.abutmentWingWallLength"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="abutmeWingWallLength"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="桥台标高：" required>
              <el-input
                v-model="model.abutmentElevation"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="abutmentElevationn"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="底板尺寸：" required>
              <el-input
                v-model="model.abutmentBaseboardSize"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="abutmentBaseboardSizee"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="挡土板厚度：" required>
              <el-input
                v-model="model.abutmentRetainThick"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="abutmentRetainThickk"
              />
            </el-form-item>
          </el-col>
          <!-- </el-form>
    </el-row>
          </div>-->
          <el-col :span="24" class="bri-size">附属工程</el-col>

          <!-- <hr>附属工程</hr>

  <div class="ass-input">
    <el-row :gutter="60">
          <el-form :model="model"  label-width="85px" @submit.native.prevent @keyup.enter.native="getList" >-->
          <el-col :span="6">
            <el-form-item label="集水口尺寸：" required>
              <el-input
                v-model="model.auxiliaryGulleySize"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="auxiliGulleySize"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="泄水管长度：" required>
              <el-input
                v-model="model.waterDrainPipeLength"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="waterDrainPiLength"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="栏杆结构：">
              <el-input
                v-model="model.auxiliaryRailStruction"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="护岸类型：">
              <el-input
                v-model="model.auxiliaryRevetmentType"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="集水口数量：" required>
              <el-input
                v-model="model.auxiliaryGulleyNum"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="auxiliGulleyNum"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="栏杆总长：" required>
              <el-input
                v-model="model.auxiliaryRailLength"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="auxiliaRaiLength"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="端柱尺寸：" required>
              <el-input
                v-model="model.auxiliaryBoundarySize"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="auxiliaBoundaSize"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="引坡挡墙类型：">
              <el-input
                v-model="model.auxiliaryBarricadeType"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="泄水管尺寸：" required>
              <el-input
                v-model="model.waterDrainPipeSize"
                clearable
                :maxlength="30"
                placeholder="30字以内"
                id="waterDraPipeSize"
              />
            </el-form-item>
          </el-col>
          <!-- </el-form>
    </el-row>
          </div>-->
          <el-col :span="24" class="bri-size">附挂管线</el-col>

          <!-- <hr>附挂管线</hr>

  <div class="ass-input">
    <el-row :gutter="60">
          <el-form :model="model"  label-width="85px" @submit.native.prevent @keyup.enter.native="getList" >-->
          <el-col :span="6">
            <el-form-item label="给水管：">
              <el-input
                v-model="model.pipelineWater"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="燃气管：">
              <el-input
                v-model="model.pipelineGas"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="电力缆：">
              <el-input
                v-model="model.pipelineElectricity"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="通信电缆：">
              <el-input
                v-model="model.pipelineCable"
                clearable
                :maxlength="30"
                placeholder="30字以内"
              />
            </el-form-item>
          </el-col>

          <el-col :span="24" class="bri-size">
            <el-button type="primary" @click="saveData">确定</el-button>
            <!-- <el-button @click="close">取消</el-button> -->
          </el-col>
        </el-form>
      </el-row>
    </div>
  </div>
</template>

<script>
// import { addInfoBridgeDetail } from "@/api/infomanage/bridgeManage";
// import { selectByPhotoDto } from "@/api/infomanage/bridgeManage";
// import { deletePhoto } from "@/api/infomanage/bridgeManage";
// import { updateBriDetailById2 } from "@/api/infomanage/bridgeManage";

export default {
  data() {
    return {
      disabled: false,
      detailDodel: [],
      model: {
        // id:"",
        structureId: '',
        structureId: this.$route.query.id,

        //   一般结构：
        //  structureName:'',
        // constructionDepartment:'',
        designLoad: '',
        bridgeTotalLength: '',
        //  roadName:'',
        // buildTime:'',
        postingStandard: '',
        bridgeTotalWidth: '',
        // runningDepartment:'',
        cost: '',
        quakeIntensity: '',
        roadwayWidth: '',
        // maintainDepartment:'',
        maintainCategory: '',
        skewAngle: '',
        sidewalkWidth: '',
        // buildingDepartment:'',
        maintainGrade: '',
        bridgeSpan: '',
        streamwayGrade: '',
        designUnits: '',
        // roadHierarchy:'',
        spanCombination: '',
        highestStage: '',
        // supervisionDepartment:'',
        structureType: '',
        bridgeArea: '',
        usualStage: '',

        //上部结构：

        upMainBeamForm: '',
        lowerLimit: '',
        upDeckComposition: '',
        upMainLongitudinalSlope: '',
        upMainBeamSize: '',
        upRiseSpan: '',
        pavementLand: '',
        upMainCrossSlope: '',
        upMainBeamQuantity: '',
        upBearingForm: '',
        upExpansionForm: '',
        upApproachLongitudinalSlope: '',
        upCrossBeamForm: '',
        upBearingNum: '',
        upExpansionQuantity: '',
        upApproachSlope: '',
        clearanceSpan: '',

        //下部结构-桥墩：
        pierForm: '',
        pierElevation: '',
        pierBaseElevation: '',
        pierNum: '',
        pierCapSize: '',
        pierFloorSize: '',
        pierPileNum: '',

        //下部结构-桥台：
        abutmentForm: '',
        abutmentBaseElevation: '',
        abutmentPileSize: '',
        abutmentWingWallForm: '',
        abutmentNum: '',
        abutmentCapSize: '',
        abutmentPileNum: '',
        abutmentWingWallLength: '',
        abutmentElevation: '',
        abutmentBaseboardSize: '',
        abutmentRetainThick: '',

        //附属工程;

        auxiliaryGulleySize: '',
        waterDrainPipeLength: '',
        auxiliaryRailStruction: '',
        auxiliaryRevetmentType: '',
        auxiliaryGulleyNum: '',
        auxiliaryRailLength: '',
        auxiliaryBoundarySize: '',
        auxiliaryBarricadeType: '',
        waterDrainPipeSize: '',

        //附挂管线:

        pipelineWater: '',
        pipelineGas: '',
        pipelineElectricity: '',
        pipelineCable: ''
      },

      starAnnexModal: false, //添加附件模态框照片
      uploadAnnexData: {
        //添加附件的数据
        table_name: 't_bridge_info_annex',
        fgn_key_id: '',
        remarks: '',
        fileName: 'bridgeAnnexfile'
      },

      starPhotoModal: false, //添加照片模态框照片
      showPhotoModal: false, //展示照片模态框
      imageUrl: '', //添加照片的地址
      uploadData: {
        //添加照片的数据
        table_name: 't_bridge_info',
        fgn_key_id: '',
        remarks: '',
        fileName: 'bridgefile'
      },
      modelName: '维修前照片',
      nowPhoto: {
        //展示当前照片的值
        remarks: '',
        url: '',
        id: ''
      },
      rowData: [], //获取行数据
      //定义查询照片数据结构
      selectPhoto: {
        tableName: '',
        fgnKeyId: ''
      },
      selectPhotoList: [], //后台获取的照片数据
      remarks: '',

      fileList: []
      // fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
    };
  },
  mounted() {
    this.saveDataInit();
  },
  // methods: {
  //     getList(){
  //       addInfoBridgeDetail(this.$route.query.id).then(res=>{
  //         this.model=res.data[0];
  //       }).catch(FileReader=>{
  //       })
  //     }
  //      }

  methods: {
    //附件的删除
    handleRemove(file, fileList) {},

    handlePreview(file) {},

    showAddAnnexModel() {
      //弹出添加附件的弹框
      this.starAnnexModal = true;
    },

    submitAnnexUpload() {
      //附件提交
      this.uploadAnnexData.fgn_key_id = this.$route.query.id;
      this.$refs.upload.submit();
    },

    handleAnnexSuccess(res, file) {
      //附件上传成功
      // this.imageUrl = URL.createObjectURL(file.raw);
      this.imageUrl = '';
      this.uploadAnnexData.remarks = '';
      this.uploadAnnexData.fgn_key_id = '';
      // this.$message({ message: "上传成功", type: "success" });

      this.$message({
        type: 'success',
        message: '上传成功!',
        showClose: true
      });
      this.starAnnexModal = false;
    },

    delPhoto() {
      //删除照片
      this.$confirm('此操作将永久删除该照片, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(() => {
          var id = this.nowPhoto.id;
          if (id == null || id == '') {
            this.$message({
              type: 'error',
              message: '图片不存在，无法删除!',
              showClose: true
            });
          } else {
            deletePhoto(id)
              .then((res) => {
                this.$message({
                  type: 'success',
                  message: '删除成功!',
                  showClose: true
                });

                this.showPhotoModal = false;
              })
              .catch((FileReader) => {
                this.$message({
                  type: 'error',
                  message: '删除失败!',
                  showClose: true
                });
              });
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
            showClose: true
          });
        });
    },
    carouselChange(element, item) {
      //切换跑马灯触发
      // this.$refs.showPhotoModal.setActiveItem(index);
      this.nowPhoto.remarks = this.selectPhotoList[element].remarks;
      this.nowPhoto.id = this.selectPhotoList[element].id;
    },
    showAddModel() {
      //弹出添照片的弹框
      this.showPhotoModal = false;
      this.starPhotoModal = true;
    },

    submitUpload() {
      //照片提交
      this.uploadData.fgn_key_id = this.$route.query.id;
      this.$refs.upload.submit();
    },

    //添加图片展示框
    showRowPhoto() {
      //  var id = this.nowPhoto.id;

      //                if(id==null||id==""){
      //           this.disabled=false;
      //                }else{
      //            this.disabled=  true;
      //                }
      //照片展示框
      this.nowPhoto.remarks = '';
      this.nowPhoto.id = '';
      // this.rowData = row;
      $('#showPhotoModalId').find('.el-dialog__title').html('添加图片');
      this.showPhotoModal = true;
      //根据表名 id 查询照片
      this.selectPhoto.tableName = 't_bridge_info';
      this.selectPhoto.fgnKeyId = this.$route.query.id;
      selectByPhotoDto(this.selectPhoto)
        .then((res) => {
          this.selectPhotoList = res.photoList;
          this.selectPhotoList = this.selectPhotoList.map((text, index) => {
            let json = {};
            json.photoUrl = text.photoUrl;
            json.id = text.id;
            json.remarks = text.remarks;
            json.index = text.index;
            return json;
          });
          if (this.selectPhotoList.length == 0) {
            this.nowPhoto.remarks = '';
            this.nowPhoto.id = '';
          } else {
            this.nowPhoto.remarks = this.selectPhotoList[0].remarks;
            this.nowPhoto.id = this.selectPhotoList[0].id;
          }
        })
        .catch((FileReader) => {});
    },

    //照片上传
    beforeAvatarUpload(file) {
      const isJPG = file.name.split('.')[1] === 'zip';
      const isLt2M = file.size / 1024 / 1024 < 30;

      if (!isJPG) {
        this.$message.error('上传需要是ZIP格式。');
      }
      if (!isLt2M) {
        this.$message.error('上传模型大小不能超过 30MB!');
      }
      return isJPG && isLt2M;
    },

    beforeAvatarUpload(file) {
      //  const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      // if (!isJPG) {
      //   this.$message.error("上传头像图片只能是 JPG 格式!");
      // }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
    },

    handleAvatarSuccess(res, file) {
      //上传成功
      // this.imageUrl = URL.createObjectURL(file.raw);
      this.imageUrl = '';
      this.uploadData.remarks = '';
      this.uploadData.fgn_key_id = '';
      this.$message({ message: '上传成功', type: 'success', showClose: true });
      this.starPhotoModal = false;
    },

    close() {
      this.$router.go(-1);
    },

    saveData() {
      var flag = true;
      //  if (this.model.structureType == "") {
      //     this.$message({ message: "结构类型不能为空", type: "warning" });
      //     flag = false;
      //     return;
      //   }

      var regular = /^\d+\.{0,1}\d*$/;
      //   if (regular.test(this.model.bridgeTotalLength)==false) {
      //       this.$message({ message: "桥梁总长请输入数字", type: "warning" ,showClose: true});
      //       $("#bridgeTotalLeng").focus();
      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.cost)==false) {
      //       this.$message({ message: "总造价请输入数字", type: "warning",showClose: true });
      //       $("#costt").focus();
      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.sidewalkWidth)==false) {
      //       this.$message({ message: "人行道净宽请输入数字", type: "warning" ,showClose: true});
      //        $("#sidewalkWid").focus();
      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.bridgeArea)==false) {
      //       this.$message({ message: "桥面面积请输入数字", type: "warning" ,showClose: true});
      //       $("#bridgeAr").focus();
      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.roadwayWidth)==false) {
      //       this.$message({ message: "车行道净宽请输入数字", type: "warning",showClose: true });
      //       $("#roadwayWid").focus();
      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.usualStage)==false) {
      //       this.$message({ message: "常水位请输入数字", type: "warning",showClose: true });
      //        $("#usualSta").focus();
      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.bridgeTotalWidth)==false) {
      //       this.$message({ message: "桥梁总宽请输入数字", type: "warning" ,showClose: true});
      //       $("#bridgeTotalWid").focus();
      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.bridgeSpan)==false) {
      //       this.$message({ message: "桥梁跨数请输入数字", type: "warning",showClose: true });
      //      $("#bridgeSp").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.highestStage)==false) {
      //       this.$message({ message: "最高水位请输入数字", type: "warning" ,showClose: true});
      //              $("#highestSta").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.upMainBeamSize)==false) {
      //       this.$message({ message: "主梁尺寸请输入数字", type: "warning" ,showClose: true});
      //              $("#upMainBeamSi").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.upMainBeamQuantity)==false) {
      //       this.$message({ message: "主梁数量请输入数字", type: "warning" ,showClose: true});
      //              $("#upMainBeamQuanti").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.lowerLimit)==false) {
      //       this.$message({ message: "桥下限高请输入数字", type: "warning",showClose: true });
      //              $("#lowerLim").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.upBearingNum)==false) {
      //       this.$message({ message: "支座数量请输入数字", type: "warning" ,showClose: true});
      //              $("#upBearingNu").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.pavementLand)==false) {
      //       this.$message({ message: "桥面铺装厚度请输入数字", type: "warning" ,showClose: true});
      //              $("#pavementLa").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.upExpansionQuantity)==false) {
      //       this.$message({ message: "伸缩缝数量请输入数字", type: "warning" ,showClose: true});
      //              $("#upExpansionQuanti").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.pierCapSize)==false) {
      //       this.$message({ message: "盖梁尺寸请输入数字", type: "warning" ,showClose: true});
      //              $("#pierCapSi").focus();

      //       flag = false;
      //       return;
      //     }

      // if (regular.test(this.model.pierFloorSize)==false) {
      //       this.$message({ message: "底板尺寸请输入数字", type: "warning" ,showClose: true});
      //              $("#pierFloSize").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.pierElevation)==false) {
      //       this.$message({ message: "桥墩标高请输入数字", type: "warning" ,showClose: true});
      //              $("#pierElevati").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.pierBaseElevation)==false) {
      //       this.$message({ message: "基地标高请输入数字", type: "warning" ,showClose: true});
      //              $("#pierBaseElevati").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.pierPileNum)==false) {
      //       this.$message({ message: "基桩根数请输入数字", type: "warning" ,showClose: true});
      //              $("#pierPileNumm").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.pierNum)==false) {
      //       this.$message({ message: "桥墩数量请输入数字", type: "warning" ,showClose: true});
      //              $("#pierNumm").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.abutmentNum)==false) {
      //       this.$message({ message: "桥台数量请输入数字", type: "warning" ,showClose: true});
      //              $("#abutmentNumm").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.abutmentElevation)==false) {
      //       this.$message({ message: "桥台标高请输入数字", type: "warning" ,showClose: true});
      //              $("#abutmentElevationn").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.abutmentCapSize)==false) {
      //       this.$message({ message: "台帽尺寸请输入数字", type: "warning" ,showClose: true});
      //              $("#abutmentCapSizee").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.abutmentBaseboardSize)==false) {
      //       this.$message({ message: "底板尺寸请输入数字", type: "warning" ,showClose: true});
      //              $("#abutmentBaseboardSizee").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.abutmentPileSize)==false) {
      //       this.$message({ message: "基桩尺寸请输入数字", type: "warning" ,showClose: true});
      //              $("#abutmentPileSizee").focus();

      //       flag = false;
      //       return;
      //     }

      //  if (regular.test(this.model.abutmentBaseElevation)==false) {
      //       this.$message({ message: "基底标高请输入数字", type: "warning" ,showClose: true});
      //              $("#abutmBaseElevation").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.abutmentRetainThick)==false) {
      //       this.$message({ message: "挡土板厚度请输入数字", type: "warning" ,showClose: true});
      //              $("#abutmentRetainThickk").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.abutmentWingWallLength)==false) {
      //       this.$message({ message: "翼墙长度请输入数字", type: "warning",showClose: true });
      //              $("#abutmeWingWallLength").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.auxiliaryGulleySize)==false) {
      //       this.$message({ message: "集水口尺寸请输入数字", type: "warning" ,showClose: true});
      //              $("#auxiliGulleySize").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.auxiliaryGulleyNum)==false) {
      //       this.$message({ message: "集水口数量请输入数字", type: "warning" ,showClose: true});
      //              $("#auxiliGulleyNum").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.waterDrainPipeSize)==false) {
      //       this.$message({ message: "泄水管尺寸请输入数字", type: "warning" ,showClose: true});
      //              $("#waterDraPipeSize").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.waterDrainPipeLength)==false) {
      //       this.$message({ message: "泄水管长度请输入数字", type: "warning" ,showClose: true});
      //              $("#waterDrainPiLength").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.auxiliaryRailLength)==false) {
      //       this.$message({ message: "栏杆总长请输入数字", type: "warning" ,showClose: true});
      //              $("#auxiliaRaiLength").focus();

      //       flag = false;
      //       return;
      //     }

      //   if (regular.test(this.model.auxiliaryBoundarySize)==false) {
      //       this.$message({ message: "端柱尺寸请输入数字", type: "warning" ,showClose: true});
      //              $("#auxiliaBoundaSize").focus();

      //       flag = false;
      //       return;
      //     }

      updateBriDetailById2(this.model)
        .then((res) => {
          //跳转回页面

          this.$router.push('bridgeManage');
        })
        .catch((FileReader) => {});
    },

    saveDataInit() {
      var flag = true;
      addInfoBridgeDetail(this.model)
        .then((res) => {})
        .catch((FileReader) => {});
    }
  }
};
</script>

<style scoped lang="scss">
.assessment {
  display: flex;
  justify-content: flex-end;
  .ass-top {
    border: solid 1px #3873ec;
    font-family: SourceHanSansCN-Regular;
    font-size: 14px;
    color: #3873ec;
    background: #141e30;
  }
}
.ass-input {
  .ass-con {
    display: flex;
    justify-content: space-between;
  }
}

.tiShCl {
  float: right;
  width: 100%;
}

.addPictures[data-v-a83326ba] {
  float: left;
  margin-left: 953px;
  background-color: #409eff;
}

.con-top {
  display: flex;
  justify-content: flex-end;
}
.bri-size {
  font-family: SourceHanSansCN-Medium;
  font-size: 16px;
  color: #3873ec;
  padding: 15px 0;
  margin: 15px 0;
  border-bottom: 1px solid #1c283a;
}

.bri-pictures {
  float: left;
  font-family: SourceHanSansCN-Medium;
  color: #3873ec;
  border-bottom: 1px solid #1c283a;
  padding: 15px 0;
  margin: 15px 0;
  margin-left: 0px;
}

.addPictures {
  float: left;
  margin-left: 900px;
  background-color: #409eff;
}

.attachment {
  float: left;
  margin-left: 20px;
  background-color: #141e30;
  color: #3873ec;
}

.fileHeader {
  // background-color: #192436;
}

.fileDown {
  // background-color: #192436;
}

.photoLb {
  // background-color: #192436;
}

.delPhotoCl {
  background-color: red;
}

.showAddModelCl {
  background-color: #409eff;
}

.submitUploadCl {
  background-color: #409eff;
}

.cannelUploadCl {
  background-color: #2f3b51;
}
.container input[type='file'] {
  display: none;
}

.el-col-6 {
  width: 21%;
  margin-left: 2.2%;
}

.unit {
  position: relative;
  .unit-st1 {
    position: absolute;
    color: #727c89;
    top: 5px;
    right: 10px;
    z-index: 1;
  }
  .unit-st {
    position: absolute;
    color: #727c89;
    top: 2px;
    right: 10px;
    z-index: 1;
  }
}
</style>

<style>
#song-back-addb .fileDown[data-v-a83326ba],
#song-back-addb .fileHeader[data-v-a83326ba],
#song-back-addb .photoLb[data-v-a83326ba] {
  /* background-color: #192436; */
}

/*按钮的颜色和交互颜色*/
#song-back-addb .el-button--primary:focus,
#song-back-addb .el-button--primary:hover {
  background-color: #3873ec !important;
}
#song-back-addb .el-button--primary {
  /* border-color: #3874ecd5 !important; */
  background-color: #419aff;
  border-radius: 4px;
  color: #fafafb;
}

#song-back-addb /deep/ .el-textarea__inner {
  background-color: #1f2d48;
  border: solid 0px #606266;
}

#song-back-addb /deep/ .el-form-item__label {
  white-space: nowrap;
  font-size: 12px;
}

#song-back-addb .el-input /deep/.el-input__inner {
  /* background: #141e30; */
  border: solid 1px #1f2d48;
}

#song-back-addb .avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
#song-back-addb .avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
#song-back-addb .avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
#addRecord.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

#song-back-addb input[type='file'] {
  display: none;
}
#song-back-addb .photo .el-dialog__header {
  background: #27364d;
}

/* 弹框标题 */
#song-back-addb .photo .el-dialog__title {
  font-family: SourceHanSansCN-Regular;
  font-size: 16px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 17px;
  letter-spacing: 0px;
  color: #a8b2c4;
}

#song-back-addb .photo .el-dialog__body {
  padding: 46px 36px;
  background-color: #27364d;
}

#song-back-addb .avatarCl {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 100%;
  max-height: 100%;
}

#song-back-addb .imgItems {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 100%;
  max-height: 100%;
}

#song-back-addb .container input[type='file'] {
  display: none;
}

#song-back-addb .el-input /deep/.el-input__inner:hover {
  border-color: #3873ec !important;
}

#song-back-addb .el-input /deep/.el-input__inner:focus {
  border-color: #3873ec !important;
}
</style>
