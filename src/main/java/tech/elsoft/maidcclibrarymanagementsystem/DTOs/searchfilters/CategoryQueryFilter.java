package tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryQueryFilter extends QueryFilters{

   private String categoryName;

  private   String categoryDescription;

}
