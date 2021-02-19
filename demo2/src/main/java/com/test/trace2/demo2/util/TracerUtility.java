//package com.test.trace2.demo2.util;
//
//import io.opentracing.Span;
//import io.opentracing.SpanContext;
//import io.opentracing.Tracer;
//import io.opentracing.propagation.Format;
//import io.opentracing.propagation.TextMap;
//import io.opentracing.propagation.TextMapAdapter;
//import io.opentracing.tag.Tags;
//import okhttp3.Request;
//
//import java.util.Iterator;
//import java.util.Map;
//
//
//public class TracerUtility {
//    public static Span startServerSpan(Tracer tracer, Map<String, String> headers, String operationName) {
//
//        Tracer.SpanBuilder spanBuilder;
//        try {
//            SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapAdapter(headers));
//            if (parentSpanCtx == null) {
//                spanBuilder = tracer.buildSpan(operationName);
//            } else {
//                spanBuilder = tracer.buildSpan(operationName).asChildOf(parentSpanCtx);
//            }
//        } catch (IllegalArgumentException e) {
//            spanBuilder = tracer.buildSpan(operationName);
//        }
//        return spanBuilder.withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_SERVER).start();
//    }
//
//    public static Span startKafkaSpan(Tracer tracer, Map<String, String> kafkaMap, String operationName) {
//
//        Tracer.SpanBuilder spanBuilder;
//        try {
//            SpanContext parentSpanCtx = tracer.extract(Format.Builtin.TEXT_MAP, new TextMapAdapter(kafkaMap));
//            if (parentSpanCtx == null) {
//                spanBuilder = tracer.buildSpan(operationName);
//            } else {
//                spanBuilder = tracer.buildSpan(operationName).asChildOf(parentSpanCtx);
//            }
//        } catch (IllegalArgumentException e) {
//            spanBuilder = tracer.buildSpan(operationName);
//        }
//        return spanBuilder.withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_SERVER).start();
//    }
//
//    public static TextMap requestBuilderCarrier(final Request.Builder builder) {
//        return new TextMap() {
//            @Override
//            public Iterator<Map.Entry<String, String>> iterator() {
//                throw new UnsupportedOperationException("carrier is write-only");
//            }
//
//            @Override
//            public void put(String key, String value) {
//                builder.addHeader(key, value);
//            }
//        };
//    }
//}
