package com.shangcheng.controller;

import com.github.pagehelper.PageInfo;
import com.shangcheng.goods.pojo.Brand;
import com.shangcheng.service.BrandService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
@CrossOrigin //开启跨域 a域名访问b域名的数据就是跨域
//http 和https也是跨域 协议不同 端口不一致也是跨域
public class BrandController {

    @Autowired
    private BrandService brandService;
    /**
     * 分页+条件查询实现
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@RequestBody Brand brand,
                                            @PathVariable(value = "page")Integer page,
                                            @PathVariable(value = "size")Integer size){
        PageInfo<Brand> pageInfo = brandService.findPage(brand, page, size);
        return new Result<PageInfo<Brand>>(true,StatusCode.OK,"分页查询成功",pageInfo);
    }
    /**
     * 分页查询实现
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@PathVariable(value = "page")Integer page,
                                    @PathVariable(value = "size")Integer size){
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        return new Result<PageInfo<Brand>>(true,StatusCode.OK,"分页查询成功",pageInfo);
    }
    /**
     * 条件查询 不带分页
     */
    @PostMapping(value = "/search")
    public Result<List<Brand>> findList(@RequestBody Brand brand){
        List<Brand> brands = brandService.findList(brand);
        return new Result<List<Brand>>(true, StatusCode.OK,"根据条件成功",brands);
    }

    /**
     * 根据id删除
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") Integer id){
        brandService.delete(id);
        return new Result(true,StatusCode.OK,"根据id删除成功");
    }
    /**
     * 修改品牌
     */
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id")Integer id ,@RequestBody Brand brand){
        brand.setId(id);
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"恭喜修改成功");
    }

    /**
     * 增加品牌
     */
    @PostMapping
    public Result add(@RequestBody Brand brand){
        brandService.add(brand);
        return new Result(true,StatusCode.OK,"增加品牌成功");
    }

    /**
     * 查询所有品牌
     */
    @GetMapping
    public Result<List<Brand>> findAll(){
        List<Brand> brands = brandService.findAll();
        //相应结果封装
        return new Result<>(true, StatusCode.OK,"查询品牌集合成功",brands);
    }

    @GetMapping(value = "/{id}")
    public Result<Brand> findById(@PathVariable(value = "id")Integer id){
        Brand brand = brandService.findById(id);
        //相应结果封装
        return new Result<>(true, StatusCode.OK,"根据id查询brand成功",brand);
    }
}
