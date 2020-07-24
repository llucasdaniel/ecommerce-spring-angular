import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Product } from '../common/product';
import { ProductCategory } from '../common/product-category';


@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrlProduct = 'http://localhost:8080/api/products';

  private baseUrlProductCategory = 'http://localhost:8080/api/product-category';

  constructor(private httpClient: HttpClient) { }

  getProductList(theCategoryId: number): Observable<Product[]> {

    const searchUrl = `${this.baseUrlProduct}/search/findByCategoryId?id=${theCategoryId}`;
    return this.httpClient.get<GetResponseProduct>(searchUrl).pipe(
      map(response => response._embedded.products)
    )
  }

  getProductListPaginate(thePage: number,
                         thePageSize: number,
                         theCategoryId: number): Observable<GetResponseProduct> {

    const searchUrl = `${this.baseUrlProduct}/search/findByCategoryId?id=${theCategoryId}`
      + `&page=${thePage}&size=${thePageSize}` ;
    return this.httpClient.get<GetResponseProduct>(searchUrl);
  }

  getProductCategoriesList(): Observable<ProductCategory[]> {

    const searchUrl = `${this.baseUrlProductCategory}`;
    return this.httpClient.get<GetResponseCategory>(searchUrl).pipe(
      map(response => response._embedded.productCategory)
    )
  }

  getProductListByName(theKeyword: string): Observable<Product[]> {

    const searchUrl = `${this.baseUrlProduct}/search/findByNameContaining?name=${theKeyword}`;
    return this.httpClient.get<GetResponseProduct>(searchUrl).pipe(
      map(response => response._embedded.products)
    )
  }

  getProductById(productId: number): Observable<Product> {
    const searchUrl = `${this.baseUrlProduct}/${productId}`;
    return this.httpClient.get<Product>(searchUrl);
  }
}


interface GetResponseProduct {
  _embedded: {
    products: Product[];
  },
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  }
}

interface GetResponseCategory {
  _embedded: {
    productCategory: ProductCategory[];
  }
}