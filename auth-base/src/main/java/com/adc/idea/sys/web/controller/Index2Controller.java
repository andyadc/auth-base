package com.adc.idea.sys.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adc.idea.common.utils.ImageUtils;
import com.adc.idea.common.utils.IoUtils;
import com.adc.idea.common.utils.web.ServletUtils;
import com.adc.idea.common.web.BaseController;
import com.adc.idea.file.dto.StoreDTO;
import com.adc.idea.file.entity.StoreInfo;
import com.adc.idea.file.service.StoreConnector;
import com.adc.idea.file.service.StoreInfoService;
import com.adc.idea.file.source.InputStreamDataSource;
import com.adc.idea.file.source.MultipartFileDataSource;
import com.adc.idea.sys.service.impl.TestAsyncTaskService;

@Controller
public class Index2Controller extends BaseController {

	@Autowired
	private StoreConnector storeConnector;
	@Autowired
	private StoreInfoService storeInfoService;
	@Autowired
	private TestAsyncTaskService taskService;
	
	@ResponseBody
	@RequestMapping("task")
	public String task() {
		long l1 = System.currentTimeMillis();
		taskService.task();
		System.out.println("Is It Works...");
		long l2 = System.currentTimeMillis();
		return "success: " + (l2 - l1) + "ms";
	}
	
	@ResponseBody
	@RequestMapping("render")
	public String render(HttpServletRequest request){
		Map<String, String> params = getRequestParams(request);
		System.out.println(params);
		return "success";
	}

	//@RequestMapping({ "", "/", "index" })
	public String index() {
		return "index";
	}

	@RequestMapping("webuploader")
	public String webuploader() {
		return "webuploader";
	}

	@RequestMapping("kindupload")
	public String kindupload() {
		return "kindupload";
	}

	@RequestMapping("um")
	public String um() {
		return "um";
	}
	
	@RequestMapping("image_upload")
	public String fileupload(RedirectAttributes redirectAttributes) {
		return "file/image_upload";
	}

	@RequestMapping("avatar-upload")
	@ResponseBody
	public String avatarUpload(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request)
			throws Exception {
		StoreDTO dto = storeConnector.saveStore("avatar", new MultipartFileDataSource(avatar));
		logger.info(dto.getModel() + " " + dto.getKey());
		return "{\"success\":true,\"id\":\"" + dto.getId() + "\"}";
	}
	
	@RequestMapping("avatar-download")
	public void avatarDownload(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StoreInfo info = storeInfoService.selectByPk(id);
		StoreDTO dto = storeConnector.getStore("avatar", info.getPath());
		InputStream is = null;
		try {
			ServletUtils.setFileDownloadHeader(request, response, info.getName());
			is = dto.getDataSource().getInputStream();
			IoUtils.copyStream(is, response.getOutputStream());
		} finally {
			if (is != null)
				is.close();
		}
	}

	@RequestMapping("avatar-view")
	@ResponseBody
	public void avatarView(@RequestParam("id") Long id, OutputStream os) throws Exception {
		StoreInfo info = storeInfoService.selectByPk(id);
		StoreDTO dto = storeConnector.getStore("avatar", info.getPath());

		IoUtils.copyStream(dto.getDataSource().getInputStream(), os);
	}

	/**
	 * 图像保存
	 */
	@RequestMapping("avatar-save")
	public String avatarSave(@RequestParam("id") Long id, @RequestParam("x1") int x1, @RequestParam("x2") int x2,
			@RequestParam("y1") int y1, @RequestParam("y2") int y2, @RequestParam("w") int w, Model model)
					throws Exception {
		StoreInfo info = storeInfoService.selectByPk(id);
		StoreDTO dto = storeConnector.getStore("avatar", info.getPath());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageUtils.zoomImage(dto.getDataSource().getInputStream(), baos, x1, y1, x2, y2);

		InputStreamDataSource isds = new InputStreamDataSource(w + ".png",
				new ByteArrayInputStream(baos.toByteArray()));
		dto = storeConnector.saveStore("avatar", isds);

		model.addAttribute("id", dto.getId());
		return "file/image_save";
	}

	/**
	 * 图形切割
	 */
	@RequestMapping("avatar-crop")
	public String avatarCrop(@RequestParam("id") Long id, Model model) throws Exception {
		StoreInfo info = storeInfoService.selectByPk(id);
		StoreDTO dto = storeConnector.getStore("avatar", info.getPath());

		BufferedImage bufferedImage = ImageIO.read(dto.getDataSource().getInputStream());
		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();

		if (height > width) {
			int h = 512;
			int w = (512 * width) / height;
			int min = w;
			model.addAttribute("h", h);
			model.addAttribute("w", w);
			model.addAttribute("min", min);
		} else {
			int w = 512;
			int h = (512 * height) / width;
			int min = h;
			model.addAttribute("h", h);
			model.addAttribute("w", w);
			model.addAttribute("min", min);
		}

		model.addAttribute("id", id);
		return "file/image_crop";
	}

}
