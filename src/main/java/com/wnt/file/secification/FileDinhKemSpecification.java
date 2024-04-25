package com.wnt.file.secification;

import java.util.Date;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.wnt.file.table.FileDinhKem;
import com.wnt.file.request.search.FileDinhKemSearchReq;

public class FileDinhKemSpecification {
	public static Specification<FileDinhKem> buildSearchQuery(final FileDinhKemSearchReq objReq) {
		return new Specification<FileDinhKem>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3571167956165654062L;

			@Override
			public Predicate toPredicate(Root<FileDinhKem> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String dataType = objReq.getDataType();
				Long dataId = objReq.getDataId();
				if (StringUtils.isNotBlank(dataType))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("dataType"), dataType)));

				if (StringUtils.isNotBlank(dataId+""))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("dataId"), dataId)));

				return predicate;
			}
		};
	}
}
