import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../common/product';
import { map } from 'rxjs/operators';
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
}


interface GetResponseProduct {
  _embedded: {
    products: Product[];
  }
}

interface GetResponseCategory {
  _embedded: {
    productCategory: ProductCategory[];
  }
}